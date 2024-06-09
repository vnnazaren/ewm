package ru.practicum.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.exceptions.EntityNotFoundException;
import ru.practicum.mainservice.exceptions.WrongParameterException;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.model.mapper.EventMapper;
import ru.practicum.mainservice.model.mapper.UserMapper;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.service.UserService;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.util.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final UserService adminUserService;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        checkTimeBeforeEventStart(newEventDto.getEventDate());

        User user = UserMapper.toUser(adminUserService.readUser(userId));
        Event event = EventMapper.toEvent(newEventDto);

        event.setCreatedOn(LocalDateTime.now());
        event.setState(Status.PENDING);
        event.setInitiator(user);

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto readEvent(long id) {
        return EventMapper.toEventFullDto(eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Событие с ID %s не найдено.",
                        id))));
    }

    @Override
    public EventFullDto readEventByUserId(long userId, long eventId) {
        User user = UserMapper.toUser(adminUserService.readUser(userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Событие с ID %s не найдено.",
                        eventId)));

        if (event.getInitiator().equals(user)) {
            return EventMapper.toEventFullDto(event);
        } else {
            throw new EntityNotFoundException(String.format(
                    "Пользователь с ID %s не создавал событие с ID %s.",
                    userId, eventId));
        }
    }

    @Override
    public Set<Event> readEventsByIdIn(Set<Long> events) {
        return eventRepository.findAllByIdIn(events);
    }

    @Override
    public List<EventShortDto> readEventsByUserId(long userId, int from, int size) {
        User user = UserMapper.toUser(adminUserService.readUser(userId));
        PageRequest page = PageRequest.of(from > 0 && size > 0 ? from / size : 0, size,
                Sort.by(Sort.Direction.ASC, "id"));

        return eventRepository.findAllByInitiator(user, page).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> searchEventsByAdmin(List<Long> users, List<Status> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size, String ipAddress, String uri) {

        PageRequest page = PageRequest.of(from > 0 && size > 0 ? from / size : 0, size);

        return eventRepository.searchEventsByAdmin(users, states, categories, rangeStart, rangeEnd, page).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> searchEventsByUser(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size, String ipAddress, String uri) {

        // todo - информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики

        PageRequest page = PageRequest.of(from > 0 && size > 0 ? from / size : 0, size);
        sort = (sort != null && sort.equals("EVENT_DATE")) ? "eventDate" : "id";

        // todo - информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие

        return eventRepository.findAllEvents(text.toLowerCase(), categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, page).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    private void checkTimeBeforeEventStart(LocalDateTime startDate) { // todo - добавить это в функцию update
        if (startDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new WrongParameterException("Событие начинается менее чем через 2 часа");
        }
    }
}