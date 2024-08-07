package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Класс DTO класса "Категория" - CATEGORY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    /**
     * Идентификатор категории
     */
    private long id;

    /**
     * Название категории
     */
    @NotBlank(message = "Имя категории должно быть указано.")
    @Size(min = 1, max = 50, message = "Длина названия категории должна быть от 1 до 50 символов.")
    private String name;
}