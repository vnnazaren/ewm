package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.mainservice.util.Const.DATE_TIME_FORMAT;

/**
 * Новое событие
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    /**
     * Краткое описание
     */
    @NotNull(message = "Идентификатор события должен быть указано.")
    @Size(min = 1, max = 2000, message = "Длина краткого описания события должна быть от 20 до 2000 символов.")
    private String annotation;

    /**
     * Полное описание события
     */
    @NotNull(message = "Описание события должно быть указано.")
    @Size(min = 20, max = 7000, message = "Длина описания события должна быть от 20 до 7000 символов.")
    private String description;

    /**
     * Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    @NotNull(message = "Дата и время события должны быть указаны.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    /**
     * Нужно ли оплачивать участие в событии
     */
    private Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    private Integer participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    private Boolean requestModeration;

    /**
     * Заголовок события
     */
    @NotBlank(message = "Название мероприятия должно быть указано.")
    private String title;

    /**
     * Id категории к которой относится событие
     */
    @NotNull(message = "Id категории должен быть указан.")
    private Long category;

    /**
     * Широта и долгота места проведения события
     */
    @NotNull(message = "Место проведения события должно быть указано.")
    private LocationDto location;
}