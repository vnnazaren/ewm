package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.mainservice.util.StateEnum;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ParticipationRequestDto {

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
     * Идентификатор заявки
     */
    private long id;

    /**
     * Идентификатор пользователя, отправившего заявку
     */
    private int requester;

    /**
     * Статус заявки
     */
    private StateEnum status;
}