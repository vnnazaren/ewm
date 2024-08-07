package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.util.EventState;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.Const.DATE_TIME_FORMAT;

/**
 * Данные для изменения информации о событии.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {

    /**
     * Новая аннотация
     */
    @Size(min = 20, max = 2000, message = "Длина описания события должна быть от 20 до 2000 символов.")
    private String annotation;

    /**
     * Новая категория
     */
    private Long category;

    /**
     * Новое описание
     */
    @Size(min = 20, max = 7000, message = "Длина описания события должна быть от 20 до 7000 символов.")
    private String description;

    /**
     * Новые дата и время на которые намечено событие.
     * Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    /**
     * Широта и долгота места проведения события
     */
    private LocationDto location;

    /**
     * Новое значение флага о платности мероприятия
     */
    private Boolean paid;

    /**
     * Новый лимит пользователей
     */
    private Integer participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие
     */
    private Boolean requestModeration;

    /**
     * Новое состояние события
     */
    private EventState stateAction;

    /**
     * Новый заголовок
     */
    @Size(min = 3, max = 120, message = "Длина названия события должна быть от 3 до 120 символов.")
    private String title;
}