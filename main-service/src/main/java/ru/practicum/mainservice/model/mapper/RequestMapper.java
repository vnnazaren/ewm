package ru.practicum.mainservice.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Request;
import ru.practicum.mainservice.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static Request toRequest(final ParticipationRequestDto participationRequestDto) {
        return Request.builder()
                .id(participationRequestDto.getId())
                .created(participationRequestDto.getCreated())
                .event(Event.builder()
                        .id(participationRequestDto.getEvent())
                        .build())
                .requester(User.builder()
                        .id(participationRequestDto.getRequester())
                        .build())
                .status(participationRequestDto.getStatus())
                .build();

    }

    public static ParticipationRequestDto toParticipationRequestDto(final Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getId())
                .requester(request.getId())
                .status(request.getStatus())
                .build();

    }
}