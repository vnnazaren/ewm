package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.NewUserRequest;
import ru.practicum.mainservice.dto.UserDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса USER
 */
public interface UserService {
    List<UserDto> readUsers(int from, int size);

    UserDto createUser(NewUserRequest newUserRequest);

    void deleteUser(long userId);
}