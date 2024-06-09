package ru.practicum.mainservice.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.EventShortDto;
import ru.practicum.mainservice.dto.NewEventDto;
import ru.practicum.mainservice.model.Category;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Location;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event toEvent(final NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .title(newEventDto.getTitle())
                .category(Category.builder()
                        .id(newEventDto.getCategory())
                        .build())
//                .location(LocationMapper.toLocation(newEventDto.getLocation()))
                .location(Location.builder()
                        .lat(newEventDto.getLocation().getLat())
                        .lon(newEventDto.getLocation().getLon())
                        .build())
                .build();
    }

    public static Event toEvent(final EventFullDto eventFullDto) {
        return Event.builder()
                .id(eventFullDto.getId())
                .annotation(eventFullDto.getAnnotation())
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .createdOn(eventFullDto.getCreatedOn())
                .description(eventFullDto.getDescription())
                .eventDate(eventFullDto.getEventDate())
                .paid(eventFullDto.getPaid())
                .participantLimit(eventFullDto.getParticipantLimit())
                .publishedOn(eventFullDto.getPublishedOn())
                .requestModeration(eventFullDto.getRequestModeration())
                .state(eventFullDto.getState())
                .title(eventFullDto.getTitle())
                .views(eventFullDto.getViews())
                .category(CategoryMapper.toCategory(eventFullDto.getCategory()))
                .initiator(UserMapper.toUser(eventFullDto.getInitiator()))
                .location(LocationMapper.toLocation(eventFullDto.getLocation()))
                .build();
    }

    public static EventFullDto toEventFullDto(final Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .build();
    }

    public static EventShortDto toEventShortDto(final Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .build();
    }
}