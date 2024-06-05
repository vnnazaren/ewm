package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Класс DTO класса "Подборка событий" - COMPILATION
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class CompilationDto {

    /**
     * Идентификатор подборки
     */
    @NotNull(message = "Идентификатор подборки должен быть указан.")
    private Long id;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    @NotNull(message = "Признак закрепления на главной странице должен быть указан.")
    private Boolean pinned;

    /**
     * Заголовок подборки
     */
    @NotBlank(message = "Заголовок подборки должен быть указан.")
    private String title;

    /**
     * Список событий входящих в подборку
     */
    private List<EventShortDto> events;
}