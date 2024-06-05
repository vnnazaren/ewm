package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.mainservice.util.StateEnum;

import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class EventRequestStatusUpdateRequest {

    /**
     * Идентификаторы запросов на участие в событии текущего пользователя
     */
    private List<Integer> requestIds;

    /**
     * Новый статус запроса на участие в событии текущего пользователя
     */
    private StateEnum status;
}