package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.model.Mapper;
import ru.practicum.ewm.storage.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-сервис HIT
 */
@RequiredArgsConstructor
@Service
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public void saveHit(HitDto hitDtoRequest) {
        hitRepository.save(Mapper.toHit(hitDtoRequest));
    }

    @Override
    public List<StatDto> getHitsInfo(List<String> uris,
                                     Boolean unique,
                                     LocalDateTime start,
                                     LocalDateTime end) {

        checkCorrectDates(start, end);

        if (uris.isEmpty()) {
            if (unique) {
                return hitRepository.getUniqueHits(start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getHits(start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return hitRepository.getUniqueHitsByUris(uris, start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getHitsByUris(uris, start, end).stream()
                        .map(Mapper::toUriStatDto)
                        .collect(Collectors.toList());
            }
        }
    }

    private void checkCorrectDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("Дата начала должна быть раньше даты окончания.");
        }
    }
}