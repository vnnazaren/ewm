package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Класс DTO для создания класса "Подборка событий" - COMPILATION
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    /**
     * Список событий входящих в подборку
     */
    private Set<Long> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private boolean pinned;

    /**
     * Заголовок подборки
     */
    @Size(min = 1, max = 50, message = "Длина заголовка подборки должна быть от 1 до 50 символов.")
    @NotBlank(message = "Заголовок подборки должен быть указан.")
    private String title;
}