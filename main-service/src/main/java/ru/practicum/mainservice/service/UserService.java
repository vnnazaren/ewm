package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.NewUserRequest;
import ru.practicum.mainservice.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    UserDto readUser(Long userId);

    List<UserDto> readUsers(List<Long> ids, int from, int size);

    void deleteUser(Long userId);
}