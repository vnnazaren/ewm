package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.NewUserRequest;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.service.UserService;

import java.util.List;

/**
 * Класс-контроллер USER
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final UserService userService;

    /**
     * Получение информации о пользователях
     * Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки), либо о конкретных (учитываются указанные идентификаторы)
     * В случае, если по заданным фильтрам не найдено ни одного пользователя, возвращает пустой список
     *
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public List<UserDto> readUsers(@RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        log.info("GET /users?from={}&size={}", from, size);
        return userService.readUsers(from, size);
    }

    /**
     * Добавление нового пользователя
     *
     * @param newUserRequest Тело запроса с DTO пользователя
     * @return Объект DTO с новым созданным пользователем
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserRequest newUserRequest) {
        log.info("POST /users - {}", newUserRequest);
        return userService.createUser(newUserRequest);
    }

    /**
     * Удаление пользователя
     *
     * @param userId Идентификатор пользователя которого необходимо удалить
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        log.info("DELETE /admin/users/{}", userId);
        userService.deleteUser(userId);
    }
}