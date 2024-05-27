package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exceptions.Marker;
import ru.practicum.ewm.user.dto.UserDto;

/**
 * Класс-контроллер USER
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient userClient;

    /**
     * Создание нового пользователя
     *
     * @param userDto Тело запроса с DTO пользователя
     * @return Объект DTO с новым созданным пользователем
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@Validated(Marker.OnCreate.class) @RequestBody UserDto userDto) {
        log.info("POST /users - {}", userDto);
        return userClient.createUser(userDto);
    }

    /**
     * Получение пользователя по идентификатору
     *
     * @param userId Идентификатор пользователя по которому необходима информация
     * @return Объект DTO пользователя
     */
    @GetMapping("{userId}")
    public ResponseEntity<Object> readUser(@PathVariable long userId) {
        log.info("GET /users/{}", userId);
        return userClient.readUser(userId);
    }

    /**
     * Получение всех пользователей
     *
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public ResponseEntity<Object> readUsers() {
        log.info("GET /users");
        return userClient.readUsers();
    }

    /**
     * Обновление данных пользователя
     *
     * @param userDto Тело запроса с DTO объектом пользователя
     * @param userId  Идентификатор пользователя которого надо изменить
     * @return Объект DTO пользователя с изменёнными полями
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@Validated(Marker.OnUpdate.class) @RequestBody UserDto userDto,
                                             @PathVariable long userId) {
        log.info("PATCH /users/{} - {}", userId, userDto);
        return userClient.updateUser(userId, userDto);
    }

    /**
     * Удаление пользователя
     *
     * @param userId Идентификатор пользователя которого необходимо удалить
     */
    @DeleteMapping("{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable long userId) {
        log.info("DELETE /users - {}", userId);
        return userClient.deleteUser(userId);
    }
}