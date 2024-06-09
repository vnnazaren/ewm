package ru.practicum.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.exceptions.BadRequest;
import ru.practicum.mainservice.exceptions.EntityNotFoundException;
import ru.practicum.mainservice.exceptions.WrongParameter;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Request;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.model.mapper.EventMapper;
import ru.practicum.mainservice.model.mapper.RequestMapper;
import ru.practicum.mainservice.model.mapper.UserMapper;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.service.RequestService;
import ru.practicum.mainservice.service.UserService;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.storage.RequestRepository;
import ru.practicum.mainservice.util.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс-сервис REQUEST
 */
@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final UserService userService;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public ParticipationRequestDto createRequests(long userId, long eventId) {
        User user = UserMapper.toUser(userService.readUser(userId));
        Event event = EventMapper.toEvent(eventService.readEventsById(eventId));

        return RequestMapper.toParticipationRequestDto(requestRepository.save(Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .build()));
    }

    @Override
    public List<ParticipationRequestDto> readRequestsByUserId(long userId, long eventId) {
        User user = UserMapper.toUser(userService.readUser(userId));
        Event event = EventMapper.toEvent(eventService.readEventsById(eventId));

        if (event.getInitiator().equals(user)) {
            return requestRepository.findAllByEvent(event).stream()
                    .map(RequestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Пользователь с ID " + userId + " не создавал событие с ID " + eventId + ".");
        }
    }

    @Override
    public List<ParticipationRequestDto> readRequests(long userId) {
        User user = UserMapper.toUser(userService.readUser(userId));

        return requestRepository.findAllByRequester(user).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequests(long userId,
                                                         long eventId,
                                                         EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        UserMapper.toUser(userService.readUser(userId));
        Event event = EventMapper.toEvent(eventService.readEvent(userId, eventId));

        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new WrongParameter("Достигнут лимит заявок на участие в событии");
        }

        List<Request> confirmed = new ArrayList<>();
        List<Request> rejected = new ArrayList<>();

        List<Request> requests = requestRepository.findAllByIdInAndStatus(
                eventRequestStatusUpdateRequest.getRequestIds(),
                Status.PENDING);

        for (Request request : requests) { // todo - этот блок переделать на стримы
            if (event.getParticipantLimit() == 0) {
                request.setStatus(Status.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            } else if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                if (!event.getRequestModeration() ||
                        (Objects.equals(eventRequestStatusUpdateRequest.getStatus(), Status.CONFIRMED))) {
                    request.setStatus(Status.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    confirmed.add(request);
                } else {
                    request.setStatus(Status.REJECTED);
                    rejected.add(request);
                }
            }
        }

        eventRepository.save(event);

        return EventRequestStatusUpdateResult.builder() // todo - этот блок переделать на нормальные стримы
                .confirmedRequests(confirmed.stream()
                        .map(RequestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()))
                .rejectedRequests(rejected.stream()
                        .map(RequestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ParticipationRequestDto cancelRequests(long userId, long requestId) {
        userService.readUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Запрос с ID %s не найден.", requestId)));

        if (request.getStatus().equals(Status.CANCELED)
                || request.getStatus().equals(Status.REJECTED)) {
            throw new BadRequest("Запрос уже отменен");
        }

        request.setStatus(Status.CANCELED);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}