package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.util.EventStatus;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    /**
     * Идентификатор заявки
     */
    private long id;

    /**
     * Дата и время создания заявки
     */
    @JsonFormat
    private LocalDateTime created;

    /**
     * Идентификатор события
     */
    private long event;

    /**
     * Идентификатор пользователя, отправившего заявку
     */
    private long requester;

    /**
     * Статус заявки
     */
    private EventStatus status;
}