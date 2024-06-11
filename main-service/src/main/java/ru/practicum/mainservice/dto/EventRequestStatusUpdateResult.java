package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {

    /**
     * Заявка на участие в событии
     */
    private List<ParticipationRequestDto> confirmedRequests;

    /**
     * Заявка на участие в событии
     */
    private List<ParticipationRequestDto> rejectedRequests;
}