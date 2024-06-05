package ru.practicum.mainservice.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.NewUserRequest;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.service.UserService;

import java.util.List;

/**
 * Класс-сервис USER
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDto> readUsers(int from, int size) {
        return List.of();
    }

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {

    }
}