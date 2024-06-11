package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.util.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventService {

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    EventFullDto readEvent(Long eventId);

    EventFullDto readEventById(Long eventId, String ip, String uri);

    EventFullDto readEventByUserIdAndEventId(Long userId, Long eventId);

    Set<Event> readEventsByIdIn(Set<Long> events);

    List<EventShortDto> readEventsByUserId(Long userId, Integer from, Integer size);

    List<EventFullDto> searchEventsByAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                           LocalDateTime startDate, LocalDateTime endDate,
                                           Integer from, Integer size);

    List<EventShortDto> searchEventsByUser(String text, List<Long> categories, Boolean paid,
                                           LocalDateTime startDate, LocalDateTime endDate,
                                           Boolean onlyAvailable, String sort,
                                           Integer from, Integer size, String ip, String uri);

    EventFullDto updateEventByAdmin(Long eventId,
                                    UpdateEventAdminRequest updateEventAdminRequest);

    EventFullDto updateEventByUser(Long userId,
                                   Long eventId,
                                   UpdateEventUserRequest updateEventUserRequest);
}