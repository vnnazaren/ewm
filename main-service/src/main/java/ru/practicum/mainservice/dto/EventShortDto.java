package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.mainservice.util.Const.DATE_TIME_FORMAT;

/**
 * Краткая информация о событии
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    /**
     * Идентификатор
     */
    private long id;

    /**
     * Краткое описание
     */
    @NotBlank(message = "Краткое описание должно быть указано.")
    private String annotation;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private int confirmedRequests;

    /**
     * Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @NotNull(message = "Дата и время события должны быть указаны.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    /**
     * Нужно ли оплачивать участие
     */
    @NotNull(message = "Должно быть указано требуется ли оплачивать участие в событии.")
    private Boolean paid;

    /**
     * Заголовок
     */
    @NotBlank(message = "Название мероприятия должно быть указано.")
    private String title;

    /**
     * Количество просмотров события
     */
    private int views;

    /**
     * Категория
     */
    @NotNull(message = "Категория должна быть указана.")
    private CategoryDto category;

    /**
     * Инициатор
     */
    @NotNull(message = "Инициатор события должен быть указан.")
    private UserShortDto initiator;
}