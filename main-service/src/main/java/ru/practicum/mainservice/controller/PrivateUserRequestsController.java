package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.service.RequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Класс контроллер REQUESTS
 * Закрытый API для работы с запросами текущего пользователя на участие в событиях
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateUserRequestsController {
    private final RequestService requestService;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     *
     * @param userId Идентификатор пользователя
     * @return Список DTO запросов на участие
     */
    @GetMapping
    public List<ParticipationRequestDto> readUserRequests(@PathVariable @NotNull final long userId) { // todo - это образец оформления переменной
        log.info("GET /users/{}/requests", userId);
        return requestService.readRequests(userId);
    }

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     *
     * @param userId  Идентификатор пользователя
     * @param eventId Идентификатор события
     * @return Объект DTO с новым созданным запросом
     */
    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequests(@PathVariable @NotNull final long userId,
                                                  @PathVariable @NotNull final long eventId) {
        log.info("POST /users/{}/requests/{}", userId, eventId);
        return requestService.createRequests(userId, eventId);
    }


    /**
     * Отмена своего запроса на участие в событии
     *
     * @param userId    Идентификатор пользователя
     * @param requestId Идентификатор запроса на участие в событии
     * @return Объект DTO категории с изменёнными полями
     */
    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequests(@PathVariable @NotNull final long userId,
                                                  @PathVariable @NotNull final long requestId) {
        log.info("PATCH /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.cancelRequests(userId, requestId);
    }
}