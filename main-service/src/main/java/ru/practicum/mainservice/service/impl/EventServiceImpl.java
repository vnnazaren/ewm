package ru.practicum.mainservice.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.util.StateEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-сервис EVENT
 */
@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<EventShortDto> readEventsByUserId(long userId, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto readEvent(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEventByUser(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public List<EventFullDto> readEvents(List<Long> users, List<StateEnum> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        return List.of();
    }

    @Override
    public EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }

    @Override
    public List<EventShortDto> searchEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer page, Integer size) {
        return List.of();
    }

    @Override
    public EventFullDto readEventsById(long id) {
        return null;
    }
}