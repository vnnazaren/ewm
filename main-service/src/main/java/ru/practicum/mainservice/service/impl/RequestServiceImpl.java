package ru.practicum.mainservice.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.service.RequestService;

import java.util.List;

/**
 * Класс-сервис REQUEST
 */
@Service
public class RequestServiceImpl implements RequestService {
    @Override
    public List<ParticipationRequestDto> readRequestsByUserId(long userId, long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(long userId, long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> readRequests(long userId) {
        return List.of();
    }

    @Override
    public ParticipationRequestDto createRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequests(long userId, long requestId) {
        return null;
    }
}