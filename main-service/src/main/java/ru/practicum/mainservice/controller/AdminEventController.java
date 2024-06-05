package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.util.StateEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-контроллер EVENT
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventController {
    private final EventService eventService;

    /**
     * Поиск событий
     *
     * @return Коллекция объектов DTO с событиями
     */
    @GetMapping
    public List<EventFullDto> readEvents(@RequestParam List<Long> users,
                                         @RequestParam List<StateEnum> states,
                                         @RequestParam List<Long> categories,
                                         @RequestParam LocalDateTime rangeStart,
                                         @RequestParam LocalDateTime rangeEnd,
                                         @RequestParam Integer from,
                                         @RequestParam Integer size) {
        log.info("GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);

        return eventService.readEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Редактирование данных события и его статуса (отклонение/публикация)
     *
     * @param updateEventAdminRequest Тело запроса с DTO события
     * @param eventId                 Идентификатор события которого надо изменить
     * @return Объект DTO события с изменёнными полями
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable long eventId,
                                    @RequestBody final UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("PATCH /admin/events/{} - {}", eventId, updateEventAdminRequest);
        return eventService.updateEventByAdmin(eventId, updateEventAdminRequest);
    }
}