package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-контроллер EVENT
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEvent(@RequestBody EventDto eventDtoRequest) {
        log.info("POST /hit - eventDtoRequest: {}", eventDtoRequest);
        eventService.saveEvent(eventDtoRequest);
    }

    @GetMapping("/stats")
    public List<StatDto> getEventsInfo(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "false") boolean unique,
            @RequestParam @Nullable List<String> uris) {
        log.info("GET /stats - uris: {}, unique: {}, start: {}, end: {}", uris, unique, start, end);
        if (uris == null) {
            uris = List.of();
        }
        return eventService.getEventsInfo(uris, unique, start, end);
    }
}