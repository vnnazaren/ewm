package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.util.EventStatus;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mainservice.util.Const.DATE_TIME_FORMAT;

/**
 * Класс-контроллер EVENT
 */
@Slf4j
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
    public List<EventFullDto> searchEventsByAdmin(@RequestParam(name = "users", required = false) List<Long> users,
                                                  @RequestParam(name = "states", required = false) List<EventStatus> states,
                                                  @RequestParam(name = "categories", required = false) List<Long> categories,
                                                  @RequestParam(name = "rangeStart", required = false)
                                                  @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime startDate,
                                                  @RequestParam(name = "rangeEnd", required = false)
                                                  @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime endDate,
                                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("AdminEventController: GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}",
                users, states, categories, startDate, endDate, from, size);

        return eventService.searchEventsByAdmin(users, states, categories, startDate, endDate, from, size);
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
    public EventFullDto updateEventByAdmin(@PathVariable(name = "eventId") Long eventId,
                                           @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("AdminEventController: PATCH /admin/events/{} - {}", eventId, updateEventAdminRequest);
        return eventService.updateEventByAdmin(eventId, updateEventAdminRequest);
    }
}