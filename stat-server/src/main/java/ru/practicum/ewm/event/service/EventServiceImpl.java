package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventDtoRequest;
import ru.practicum.ewm.event.dto.Mapper;
import ru.practicum.ewm.event.dto.UriStatDto;
import ru.practicum.ewm.event.storage.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public void saveEvent(EventDtoRequest eventDtoRequest) {
        eventRepository.save(Mapper.toEvent(eventDtoRequest));
    }

    @Override
    public List<UriStatDto> getEventsInfo(List<String> uris, Boolean unique, LocalDateTime start, LocalDateTime end) {

        if (unique){
            return eventRepository.getUniqueHits(uris, start, end).stream()
                    .map(Mapper::toUriStatDto)
                    .collect(Collectors.toList());
        } else {
            return eventRepository.getHits(uris, start, end).stream()
                    .map(Mapper::toUriStatDto)
                    .collect(Collectors.toList());
        }
    }
}