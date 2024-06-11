package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса REQUEST
 */
public interface RequestService {

    ParticipationRequestDto createRequests(Long userId, Long eventId);

    List<ParticipationRequestDto> readRequestsByUserId(Long userId, Long eventId);

    List<ParticipationRequestDto> readRequests(Long userId);

    EventRequestStatusUpdateResult updateRequests(Long userId,
                                                  Long eventId,
                                                  EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    ParticipationRequestDto cancelRequests(Long userId, Long requestId);
}