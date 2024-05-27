package ru.practicum.ewm.user.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.exceptions.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Класс DTO класса пользователя USER
 */
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserDto {
    /**
     * Уникальный идентификатор пользователя
     */
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Имя или логин пользователя
     */
    @NotBlank(groups = Marker.OnCreate.class,
            message = "Имя не должно быть пустым.")
    private String name;

    /**
     * Адрес электронной почты
     */
    @NotBlank(groups = Marker.OnCreate.class,
            message = "E-mail должен быть указан.")
    @Email(groups = {Marker.OnCreate.class, Marker.OnUpdate.class},
            message = "E-mail должен быть корректным.")
    private String email;
}