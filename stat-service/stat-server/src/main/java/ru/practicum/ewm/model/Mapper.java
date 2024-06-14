package ru.practicum.ewm.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatDto;

/**
 * Класс-маппер
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    public static Hit toHit(final HitDto hitDto) {
        return Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ipAddress(hitDto.getIp())
                .hitDate(hitDto.getTimestamp())
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