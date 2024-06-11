package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.service.RequestService;

import java.util.List;

/**
 * Класс контроллер REQUESTS
 * Закрытый API для работы с запросами текущего пользователя на участие в событиях
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestsController {
    private final RequestService requestService;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях.
     * <br/>
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     *
     * @param userId Идентификатор пользователя
     * @return Список DTO запросов на участие
     */
    @GetMapping
    public List<ParticipationRequestDto> readUserRequests(@PathVariable Long userId) {
        log.info("PrivateRequestsController: GET /users/{}/requests", userId);
        return requestService.readRequests(userId);
    }

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     * <li>нельзя добавить повторный запрос (Ожидается код ошибки 409)
     * <li>инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
     * <li>нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
     * <li>если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
     * <li>если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
     *
     * @param userId  Идентификатор пользователя
     * @param eventId Идентификатор события
     * @return Объект DTO с новым созданным запросом
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequests(@PathVariable Long userId,
                                                  @RequestParam Long eventId) {
        log.info("PrivateRequestsController: POST /users/{}/requests?eventId={}", userId, eventId);
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
    public ParticipationRequestDto cancelRequests(@PathVariable Long userId,
                                                  @PathVariable Long requestId) {
        log.info("PrivateRequestsController: PATCH /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.cancelRequests(userId, requestId);
    }
}