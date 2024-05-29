package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс класса-сервиса EVENT
 */
public interface EventService {

    void saveEvent(EventDto eventDtoRequest);

    List<StatDto> getEventsInfo(List<String> uris,
                                Boolean unique,
                                LocalDateTime start,
                                LocalDateTime end);
}