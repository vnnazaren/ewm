package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Пользователь (краткая информация)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
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
    private String name;
}