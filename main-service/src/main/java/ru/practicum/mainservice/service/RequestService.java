package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса REQUEST
 */
public interface RequestService {

    List<ParticipationRequestDto> readRequestsByUserId(long userId, long eventId);

    EventRequestStatusUpdateResult updateRequests(long userId,
                                                  long eventId,
                                                  EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    List<ParticipationRequestDto> readRequests(long userId);

    ParticipationRequestDto createRequests(long userId, long eventId);

    ParticipationRequestDto cancelRequests(long userId, long requestId);
}