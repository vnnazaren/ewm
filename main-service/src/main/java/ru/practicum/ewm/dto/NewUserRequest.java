package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные нового пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {

    /**
     * Имя
     */
    @NotBlank(message = "Имя пользователя должно быть указано.")
    @Size(min = 2, max = 250, message = "Длина имени должна быть от 2 до 250 символов.")
    private String name;

    /**
     * Почтовый адрес
     */
    @NotBlank(message = "E-mail должен быть указан.")
    @Email(message = "E-mail должен быть корректным.")
    @Size(min = 6, max = 254, message = "Длина адреса электронной почты должно быть от 6 до 254 символов.")
    private String email;
}