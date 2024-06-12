package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Пользователь (краткая информация)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {

    /**
     * Идентификатор
     */
    @NotNull(message = "Идентификатор должен быть указан.")
    private long id;

    /**
     * Имя
     */
    @NotBlank(message = "Имя пользователя должно быть указано.")
    @Size(min = 2, max = 250, message = "Длина имени должна быть от 2 до 250 символов.")
    private String name;
}