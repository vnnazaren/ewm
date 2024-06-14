package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.ConflictException;
import ru.practicum.ewm.exceptions.EntityNotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;
import ru.practicum.ewm.service.UserService;
import ru.practicum.ewm.storage.EventRepository;
import ru.practicum.ewm.storage.RequestRepository;
import ru.practicum.ewm.util.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final UserService userService;
    private final EventService eventService;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public ParticipationRequestDto createRequests(Long userId, Long eventId) {
        User user = UserMapper.toUser(userService.readUser(userId));
        Event event = EventMapper.toEvent(eventService.readEvent(eventId));

        if (userId.equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format("Инициатор запроса (id %s) " +
                    "не должен создавать запрос на своё событие (id %s).", userId, eventId));
        }

        if (!requestRepository.findByRequesterIdAndEventId(userId, eventId).isEmpty()) {
            throw new ConflictException(String.format("Попытка создать повторный запрос " +
                    "на участие в событии (id %s) от пользователя (id %s)", userId, eventId));
        }

        if (event.getState() != EventStatus.PUBLISHED) {
            throw new ConflictException("Нельзя добавить запрос на участие в неопубликованном событии");
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(EventStatus.PENDING)
                .build();

        int confirmed = event.getConfirmedRequests();
        int limit = event.getParticipantLimit();

        if (limit == 0) {
            event.setConfirmedRequests(confirmed + 1);
            eventRepository.save(event);
            request.setStatus(EventStatus.CONFIRMED);
        } else if (confirmed < limit) {
            if (!event.getRequestModeration()) {
                event.setConfirmedRequests(confirmed + 1);
                eventRepository.save(event);
                request.setStatus(EventStatus.PENDING);
            }
        } else {
            throw new ConflictException(String.format("Мест для участия в событии (id '%s') больше нет.",
                    eventId));
        }

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> readRequests(Long userId) {
        User user = UserMapper.toUser(userService.readUser(userId));

        return requestRepository.findAllByRequester(user).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipationRequestDto> readRequestsByUserId(Long userId, Long eventId) {
        User user = UserMapper.toUser(userService.readUser(userId));
        Event event = EventMapper.toEvent(eventService.readEvent(eventId));

        User user2 = event.getInitiator();

        System.out.println(user);
        System.out.println(user2);

        if (event.getInitiator().getId().equals(user.getId())) {
            return requestRepository.findAllByEvent(event).stream()
                    .map(RequestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException(String.format("Пользователь с ID %s не создавал событие с ID %s.",
                    userId, eventId));
        }
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequests(Long userId,
                                                         Long eventId,
                                                         EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        userService.readUser(userId);
        Event event = EventMapper.toEvent(eventService.readEvent(eventId));

        int confirmedRequests = event.getConfirmedRequests();
        int participantLimit = event.getParticipantLimit();

        if (participantLimit != 0 && confirmedRequests >= participantLimit) {
            throw new ConflictException(String.format("Больше нет мест для участия в событии с ID %s",
                    event.getId()));
        }

        List<Request> requests = requestRepository.findAllByIdInAndStatus(
                eventRequestStatusUpdateRequest.getRequestIds(),
                EventStatus.PENDING);

        List<Request> confirmed = new ArrayList<>();
        List<Request> rejected = new ArrayList<>();

        if (event.getParticipantLimit() == 0) {
            requests.forEach(request -> {
                request.setStatus(EventStatus.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            });
        } else {
            requests.forEach(request -> {
                if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    if (!event.getRequestModeration()
                            || eventRequestStatusUpdateRequest.getStatus().equals(EventStatus.CONFIRMED)) {
                        request.setStatus(EventStatus.CONFIRMED);
                        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        confirmed.add(request);
                    } else {
                        request.setStatus(EventStatus.REJECTED);
                        rejected.add(request);
                    }
                }
            });
        }

        eventRepository.save(event);

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed.stream()
                        .map(RequestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()))
                .rejectedRequests(rejected.stream()
                        .map(RequestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequests(Long userId, Long requestId) {
        userService.readUser(userId);

        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Запрос с ID %s от пользователя c ID %s не найден.",
                        requestId, userId)));

        if (request.getStatus().equals(EventStatus.CANCELED)
                || request.getStatus().equals(EventStatus.REJECTED)) {
            throw new BadRequestException("Запрос уже отменен");
        }

        request.setStatus(EventStatus.CANCELED);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}