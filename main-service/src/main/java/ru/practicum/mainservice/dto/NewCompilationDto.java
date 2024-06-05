package ru.practicum.mainservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Класс DTO для создания класса "Подборка событий" - COMPILATION
 */
public class NewCompilationDto {

    /**
     * Список событий входящих в подборку
     */
    private List<EventShortDto> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private boolean pinned; // todo - как сделать эту переменную по умолчанию false?

    /**
     * Заголовок подборки
     */
    @Size(min = 1, max = 50, message = "Длина заголовка подборки должна быть от 1 до 50 символов.")
    @NotBlank(message = "Заголовок подборки должен быть указан.")
    private String title;
}