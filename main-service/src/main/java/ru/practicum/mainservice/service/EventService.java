package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.util.StateEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс класса-сервиса EVENT
 */
public interface EventService {
    List<EventShortDto> readEventsByUserId(long userId, int from, int size);

    EventFullDto createEvent(long userId, NewEventDto newEventDto);

    EventFullDto readEvent(long userId, long eventId);

    EventFullDto updateEventByUser(long userId,
                                   long eventId,
                                   UpdateEventUserRequest updateEventUserRequest);

    List<EventFullDto> readEvents(List<Long> users,
                                  List<StateEnum> states,
                                  List<Long> categories,
                                  LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  Integer from,
                                  Integer size);

    EventFullDto updateEventByAdmin(long eventId,
                                    UpdateEventAdminRequest updateEventAdminRequest);

    List<EventShortDto> searchEvents(String text,
                                     List<Long> categories,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     Boolean onlyAvailable,
                                     String sort,
                                     Integer page,
                                     Integer size);

    EventFullDto readEventsById(long id);
}