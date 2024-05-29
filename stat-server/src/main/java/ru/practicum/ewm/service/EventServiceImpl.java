package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.model.Mapper;
import ru.practicum.ewm.storage.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-сервис EVENT
 */
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public void saveEvent(EventDto eventDtoRequest) {
        eventRepository.save(Mapper.toEvent(eventDtoRequest));
    }

    @Override
    public List<StatDto> getEventsInfo(List<String> uris, Boolean unique, LocalDateTime start, LocalDateTime end) {

        if (uris.isEmpty()) {
            if (unique) {
                return eventRepository.getUniqueHits(start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            } else {
                return eventRepository.getHits(start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return eventRepository.getUniqueHitsByUris(uris, start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            } else {
                return eventRepository.getHitsByUris(uris, start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            }
        }
    }
}