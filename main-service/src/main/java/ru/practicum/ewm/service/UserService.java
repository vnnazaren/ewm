package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса USER
 */
public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    UserDto readUser(Long userId);

    List<UserDto> readUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);
}