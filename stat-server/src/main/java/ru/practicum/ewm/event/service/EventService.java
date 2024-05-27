package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventDtoRequest;
import ru.practicum.ewm.event.dto.UriStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    void saveEvent(EventDtoRequest eventDtoRequest);

    List<UriStatDto> getEventsInfo(List<String> uris, Boolean unique, LocalDateTime start, LocalDateTime end);
}
