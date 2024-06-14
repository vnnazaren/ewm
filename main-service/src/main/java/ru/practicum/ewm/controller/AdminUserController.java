package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Класс-контроллер USER
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final UserService adminUserService;

    /**
     * Получение информации о пользователях
     * Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки), либо о конкретных (учитываются указанные идентификаторы)
     * В случае, если по заданным фильтрам не найдено ни одного пользователя, возвращает пустой список
     *
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public List<UserDto> readUsers(@RequestParam(required = false) List<Long> ids,
                                   @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                   @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("AdminUserController: GET /users?ids={}&from={}&size={}", ids, from, size);
        return adminUserService.readUsers(ids, from, size);
    }

    /**
     * Добавление нового пользователя
     *
     * @param newUserRequest Тело запроса с DTO пользователя
     * @return Объект DTO с новым созданным пользователем
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("AdminUserController: POST /users - {}", newUserRequest);
        return adminUserService.createUser(newUserRequest);
    }

    /**
     * Удаление пользователя
     *
     * @param userId Идентификатор пользователя которого необходимо удалить
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.info("AdminUserController: DELETE /admin/users/{}", userId);
        adminUserService.deleteUser(userId);
    }
}