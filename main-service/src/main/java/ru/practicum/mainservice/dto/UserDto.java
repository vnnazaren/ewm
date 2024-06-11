package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Пользователь
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * Идентификатор
     */
    private long id;

    /**
     * Имя
     */
    @NotBlank(message = "Имя пользователя должно быть указано.")
    private String name;

    /**
     * Почтовый адрес
     */
    @NotBlank(message = "E-mail должен быть указан.")
    @Email(message = "E-mail должен быть корректным.")
    private String email;
}