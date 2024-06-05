package ru.practicum.mainservice.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.model.User;

/**
 * Класс-маппер USER
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}