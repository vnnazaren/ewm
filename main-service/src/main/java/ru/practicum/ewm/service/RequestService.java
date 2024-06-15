package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса REQUEST
 */
public interface RequestService {

    ParticipationRequestDto createRequests(Long userId, Long eventId);

    List<ParticipationRequestDto> readRequestsByEventInitiatorId(Long userId, Long eventId);

    List<ParticipationRequestDto> readRequests(Long userId);

    EventRequestStatusUpdateResult updateRequests(Long userId,
                                                  Long eventId,
                                                  EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    ParticipationRequestDto cancelRequests(Long userId, Long requestId);

    void checkRequestByUserIdAndEventId(Long userId, Long eventId);
}