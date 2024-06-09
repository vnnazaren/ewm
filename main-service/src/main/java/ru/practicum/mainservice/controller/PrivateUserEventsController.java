package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.service.RequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Класс-контроллер EVENT
 * Закрытый API для работы с событиями
 */
@Slf4j
@Validated // todo - добавить @Positive и @PositiveOrZero
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateUserEventsController {
    private final EventService eventService;
    private final RequestService requestService;

    /**
     * Добавление нового события
     *
     * @param userId      Id текущего пользователя
     * @param newEventDto DTO класса EVENT для создания нового события
     * @return Коллекция объектов DTO с событиями
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@NotNull @PathVariable long userId,
                                    @RequestBody NewEventDto newEventDto) {
        log.info("POST /users/{}/events - {}", userId, newEventDto);
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
    public EventFullDto readEvent(@NotNull @PathVariable long userId,
                                  @NotNull @PathVariable long eventId) {
        log.info("POST /users/{}/events/{}", userId, eventId);
        return eventService.readEvent(userId, eventId);
    }

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId Id текущего пользователя
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public List<EventShortDto> readEventsByUserId(@PathVariable long userId,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info("GET /users/{}/events?from={}&size={}", userId, from, size);
        return eventService.readEventsByUserId(userId, from, size);
    }

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId  Id текущего пользователя
     * @param eventId Id события
     * @return Объект DTO запроса на участие
     */
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> readRequestsByUserId(@NotNull @PathVariable long userId,
                                                              @NotNull @PathVariable long eventId) {
        log.info("POST /users/{}/events/{}/requests", userId, eventId);
        return requestService.readRequestsByUserId(userId, eventId);
    }

    /**
     * Изменение события добавленного текущим пользователем.
     * Изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409).
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409).
     *
     * @param userId  Id текущего пользователя
     * @param eventId Id события
     * @return Объект DTO события
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByUser(@NotNull @PathVariable long userId,
                                          @NotNull @PathVariable long eventId,
                                          @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        log.info("PATCH DTO {}", updateEventUserRequest);
        return eventService.updateEventByUser(userId, eventId, updateEventUserRequest);
    }

    /**
     * Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя.
     * <li>Если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется.
     * <li>Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409).
     * <li>Статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409).
     * <li>Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить.
     *
     * @param userId  Id текущего пользователя
     * @param eventId Id события
     * @param eventRequestStatusUpdateRequest DTO с новым статусом и списком заявок на участие в событии текущего пользователя
     * @return Объект DTO запроса на участие
     */
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@NotNull @PathVariable long userId,
                                                         @NotNull @PathVariable long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("PATCH /users/{}/events/{}/requests - {}", userId, eventId, eventRequestStatusUpdateRequest);
        return requestService.updateRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }
}