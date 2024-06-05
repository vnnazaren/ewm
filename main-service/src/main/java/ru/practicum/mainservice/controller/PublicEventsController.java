package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.EventShortDto;
import ru.practicum.mainservice.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final EventService eventService;

    /**
     * Получение событий с возможностью фильтрации
     *
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public List<EventShortDto> searchEvents(@RequestParam String text,
                                            @RequestParam List<Long> categories,
                                            @RequestParam Boolean paid,
                                            @RequestParam LocalDateTime rangeStart,
                                            @RequestParam LocalDateTime rangeEnd,
                                            @RequestParam Boolean onlyAvailable,
                                            @RequestParam String sort,
                                            @RequestParam Integer page,
                                            @RequestParam Integer size) {
        log.info("GET /events?text={}&categories={}&paid={}&rangeStart={}&rangeEnd={}&onlyAvailable={}&sort={}&from={}&size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, page, size);
        return eventService.searchEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, page, size);
    }

    /**
     * Получение событий с возможностью фильтрации
     *
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping("/{id}")
    public EventFullDto readEventById(@PathVariable long id) {
        log.info("GET /events/{}", id);
        return eventService.readEventsById(id);
    }
}