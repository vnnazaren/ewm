package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Класс-контроллер EVENT
 * Закрытый API для работы с событиями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {
    private final EventService eventService;
    private final RequestService requestService;

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId Id текущего пользователя
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public List<EventShortDto> readEventsByUserId(@PathVariable @Positive Long userId,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("PrivateEventsController: GET /users/{}/events?from={}&size={}", userId, from, size);
        return eventService.readEventsByUserId(userId, from, size);
    }

    /**
     * Добавление нового события
     *
     * @param userId      Id текущего пользователя
     * @param newEventDto DTO класса EVENT для создания нового события
     * @return Коллекция объектов DTO с событиями
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable @Positive Long userId,
                                    @RequestBody @Valid NewEventDto newEventDto) {
        log.info("PrivateEventsController: POST /users/{}/events - {}", userId, newEventDto);
        return eventService.createEvent(userId, newEventDto);
    }

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     *
     * @param userId  Id текущего пользователя
     * @param eventId Id события
     * @return Объект DTO события
     */
    @GetMapping("/{eventId}")
    public EventFullDto readEventByUserIdAndEventId(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId) {
        log.info("PrivateEventsController: POST /users/{}/events/{}", userId, eventId);
        return eventService.readEventByUserIdAndEventId(userId, eventId);
    }

    /**
     * Изменение события добавленного текущим пользователем.<br/>
     * Изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409).<br/>
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409).
     *
     * @param userId  Id текущего пользователя
     * @param eventId Id события
     * @return Объект DTO события
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable @Positive Long userId,
                                          @PathVariable @Positive Long eventId,
                                          @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("PrivateEventsController: PATCH /users/{}/events/{} - DTO - {}",
                userId, eventId, updateEventUserRequest);
        return eventService.updateEventByUser(userId, eventId, updateEventUserRequest);
    }

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId  Id текущего пользователя
     * @param eventId Id события
     * @return Объект DTO запроса на участие
     */
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> readRequestsByUserId(@PathVariable Long userId,
                                                              @PathVariable Long eventId) {
        log.info("PrivateEventsController: POST /users/{}/events/{}/requests", userId, eventId);
        return requestService.readRequestsByEventInitiatorId(userId, eventId);
    }

    /**
     * Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя.
     * <li>Если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется.
     * <li>Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409).
     * <li>Статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409).
     * <li>Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить.
     *
     * @param userId                          Id текущего пользователя
     * @param eventId                         Id события
     * @param eventRequestStatusUpdateRequest DTO с новым статусом и списком заявок на участие в событии текущего пользователя
     * @return Объект DTO запроса на участие
     */
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@PathVariable @Positive Long userId,
                                                         @PathVariable @Positive Long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("PrivateEventsController: PATCH /users/{}/events/{}/requests - {}", userId, eventId, eventRequestStatusUpdateRequest);
        return requestService.updateRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }
}