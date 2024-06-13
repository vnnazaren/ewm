package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.Util.Const.DATE_TIME_FORMAT;

/**
 * Класс-контроллер HIT
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveHit(@RequestBody HitDto hitDtoRequest) {
        log.info("POST /hit - hitDtoRequest: {}", hitDtoRequest);
        hitService.saveHit(hitDtoRequest);
    }

    @GetMapping("/stats")
    public List<StatDto> getHitsInfo(
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
            @RequestParam(defaultValue = "false") boolean unique,
            @RequestParam @Nullable List<String> uris) {
        log.info("GET /stats - uris: {}, unique: {}, start: {}, end: {}", uris, unique, start, end);
        if (uris == null) {
            uris = List.of();
        }
        return hitService.getHitsInfo(uris, unique, start, end);
    }
}