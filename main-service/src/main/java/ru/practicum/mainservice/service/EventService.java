package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.util.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс класса-сервиса EVENT
 */
public interface EventService {

    EventFullDto createEvent(long userId, NewEventDto newEventDto);

    EventFullDto readEvent(long userId, long eventId);

    EventFullDto readEventsById(long id);

    Set<Event> readEventsByIdIn(Set<Long> events);

    List<EventShortDto> readEventsByUserId(long userId, int from, int size);

    List<EventFullDto> searchEventsByAdmin(List<Long> users,
                                           List<Status> states,
                                           List<Long> categories,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Integer from,
                                           Integer size);

    List<EventShortDto> searchEventsByUser(String text,
                                           List<Long> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable,
                                           String sort,
                                           Integer page,
                                           Integer size);

    EventFullDto updateEventByAdmin(long eventId,
                                    UpdateEventAdminRequest updateEventAdminRequest);

    EventFullDto updateEventByUser(long userId,
                                   long eventId,
                                   UpdateEventUserRequest updateEventUserRequest);
}