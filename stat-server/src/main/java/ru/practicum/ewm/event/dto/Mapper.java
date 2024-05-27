package ru.practicum.ewm.event.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.UriStat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    public static Event toEvent(final EventDtoRequest eventDtoRequest) {
        return Event.builder()
                .app(eventDtoRequest.getApp())
                .uri(eventDtoRequest.getUri())
                .ipAddress(eventDtoRequest.getIp())
                .eventDate(eventDtoRequest.getTimestamp())
                .build();
    }

    public static UriStatDto toUriStatDto(final UriStat uriStat) {
        return UriStatDto.builder()
                .app(uriStat.getApp())
                .uri(uriStat.getUri())
                .build();
    }
}