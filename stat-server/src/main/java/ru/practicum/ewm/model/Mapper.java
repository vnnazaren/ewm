package ru.practicum.ewm.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.StatDto;

/**
 * Класс-маппер
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    public static Event toEvent(final EventDto eventDto) {
        return Event.builder()
                .app(eventDto.getApp())
                .uri(eventDto.getUri())
                .ipAddress(eventDto.getIp())
                .eventDate(eventDto.getTimestamp())
                .build();
    }

    public static StatDto toUriStatDto(final Stat stat) {
        return StatDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(stat.getHits())
                .build();
    }
}