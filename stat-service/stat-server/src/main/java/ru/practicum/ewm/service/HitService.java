package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс класса-сервиса HIT
 */
public interface HitService {

    void saveHit(HitDto hitDtoRequest);

    List<StatDto> getHitsInfo(List<String> uris,
                              Boolean unique,
                              LocalDateTime start,
                              LocalDateTime end);
}