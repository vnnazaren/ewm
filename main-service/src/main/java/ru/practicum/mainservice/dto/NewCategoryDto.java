package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные для добавления новой категории
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {

    /**
     * Название категории
     */
    @NotBlank(message = "Имя категории должно быть указано.")
    @Size(min = 1, max = 50, message = "Длина названия категории должна быть от 1 до 50 символов.")
    private String name;
}