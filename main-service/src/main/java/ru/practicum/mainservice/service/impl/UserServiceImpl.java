package ru.practicum.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.NewUserRequest;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.exceptions.EntityNotFoundException;
import ru.practicum.mainservice.model.mapper.UserMapper;
import ru.practicum.mainservice.service.UserService;
import ru.practicum.mainservice.storage.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-сервис USER
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequest)));
    }

    @Override
    public UserDto readUser(Long userId) {
        return UserMapper.toUserDto(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + userId + " не найден.")));
    }

    @Override
    public List<UserDto> readUsers(int from, int size) {
        PageRequest page = PageRequest.of(from > 0 && size > 0 ? from / size : 0, size);
        return userRepository.findAll(page).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}