package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.mainservice.util.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Событие
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class EventFullDto {

    /**
     * Идентификатор события
     */
    @NotNull(message = "Идентификатор события должен быть указано.")
    private long id;

    /**
     * Краткое описание
     */
    @NotNull(message = "Краткое описание должно быть указано.")
    private String annotation;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private int confirmedRequests;

    /**
     * Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
     */
    private LocalDateTime createdOn;

    /**
     * Полное описание события
     */
    private String description;

    /**
     * Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @NotNull(message = "Дата и время события должны быть указаны.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * Нужно ли оплачивать участие
     */
    @NotNull(message = "Должно быть указано требуется ли оплачивать участие в событии.")
    private Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    private Integer participantLimit;

    /**
     * Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
     */
    private LocalDateTime publishedOn;

    /**
     * Нужна ли пре-модерация заявок на участие
     */
    private Boolean requestModeration;

    /**
     * Список состояний жизненного цикла события
     */
    private Status state;

    /**
     * Заголовок
     */
    @NotNull(message = "Название мероприятия должно быть указано.")
    private String title;

    /**
     * Количество просмотров события
     */
    private Integer views;

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

    /**
     * Широта и долгота места проведения события
     */
    @NotNull(message = "Место проведения события должно быть указано.")
    private LocationDto location;
}