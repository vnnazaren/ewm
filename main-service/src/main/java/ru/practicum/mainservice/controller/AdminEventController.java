package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.util.Status;

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
     * Поиск событий<br/>
     * Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия.
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список.
     *
     * @return Коллекция объектов DTO с событиями
     */
    @GetMapping
    public List<EventFullDto> searchEventsByAdmin(@RequestParam List<Long> users,
                                                  @RequestParam List<Status> states,
                                                  @RequestParam List<Long> categories,
                                                  @RequestParam LocalDateTime rangeStart,
                                                  @RequestParam LocalDateTime rangeEnd,
                                                  @RequestParam Integer from,
                                                  @RequestParam Integer size) {
        log.info("GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);

        return eventService.searchEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Редактирование данных события и его статуса (отклонение/публикация).<br/>
     * Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:
     * <li>дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
     * <li>событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
     * <li>событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
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