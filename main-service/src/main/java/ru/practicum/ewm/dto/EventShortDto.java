package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.Const.DATE_TIME_FORMAT;

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
    private String annotation;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private int confirmedRequests;

    /**
     * Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    /**
     * Нужно ли оплачивать участие
     */
    private Boolean paid;

    /**
     * Заголовок
     */
    private String title;

    /**
     * Количество просмотров события
     */
    private int views;

    /**
     * Категория
     */
    private CategoryDto category;

    /**
     * Инициатор
     */
    private UserShortDto initiator;

    /**
     * Комментарии к событию
     */
    private List<CommentDto> comments;
}