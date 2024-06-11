package ru.practicum.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.StatClient;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.exceptions.ConflictException;
import ru.practicum.mainservice.exceptions.EntityNotFoundException;
import ru.practicum.mainservice.exceptions.WrongParameterException;
import ru.practicum.mainservice.mapper.CategoryMapper;
import ru.practicum.mainservice.mapper.EventMapper;
import ru.practicum.mainservice.mapper.LocationMapper;
import ru.practicum.mainservice.mapper.UserMapper;
import ru.practicum.mainservice.model.Category;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Location;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.service.CategoryService;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.service.UserService;
import ru.practicum.mainservice.storage.EventRepository;
import ru.practicum.mainservice.util.EventState;
import ru.practicum.mainservice.util.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.util.Const.HOURS_BEFORE_EVENT_ADMIN;
import static ru.practicum.mainservice.util.Const.HOURS_BEFORE_EVENT_USER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final StatClient statClient;

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto eventNewDto) {

        checkTimeBeforeEventStart(eventNewDto.getEventDate(), HOURS_BEFORE_EVENT_USER);

        Event event = EventMapper.toEvent(eventNewDto);
        Category category = CategoryMapper.toCategory(categoryService.readCategory(eventNewDto.getCategory()));
        User user = UserMapper.toUser(userService.readUser(userId));

        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventStatus.PENDING);

        Event savedEvent = eventRepository.save(event);

        return EventMapper.toEventFullDto(savedEvent);
    }

    @Override
    public EventFullDto readEvent(Long id) {
        return EventMapper.toEventFullDto(eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Событие с ID %s не найдено.", id))));
    }

    @Override
    public EventFullDto readEventById(Long eventId, String ip, String uri) {
        Event event = eventRepository.findByIdAndState(eventId, EventStatus.PUBLISHED)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        saveInfoToStatistics(ip, uri);
        updateViewsOfEvents(List.of(event));

        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto readEventByUserIdAndEventId(Long userId, Long eventId) {
        User user = UserMapper.toUser(userService.readUser(userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Событие с ID %s не найдено.",
                        eventId)));

        if (event.getInitiator().equals(user)) {
            return EventMapper.toEventFullDto(event);
        } else {
            throw new ConflictException(String.format("Пользователь с ID %s не создавал событие с ID %s.",
                    userId, eventId));
        }
    }

    @Override
    public List<EventShortDto> readEventsByUserId(Long userId, Integer from, Integer size) {
        User user = UserMapper.toUser(userService.readUser(userId));
        PageRequest page = PageRequest.of(from > 0 && size > 0 ? from / size : 0, size,
                Sort.by(Sort.Direction.ASC, "id"));

        return eventRepository.findAllByInitiator(user, page).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Event> readEventsByIdIn(Set<Long> events) {
        return eventRepository.findAllByIdIn(events);
    }


    @Override
    public List<EventFullDto> searchEventsByAdmin(List<Long> users, List<EventStatus> states, List<Long> categories, // todo - переделать, особенно после изучения спецификаций
                                                  LocalDateTime startDate, LocalDateTime endDate,
                                                  Integer from, Integer size) {

        PageRequest page = PageRequest.of(from / size, size, Sort.by(
                Sort.Direction.ASC, "id"));

        Specification<Event> specification = Specification.where(null);

        if (startDate != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), startDate));
        }

        if (endDate != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), endDate));
        }

        if (users != null && !users.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> root.get("initiator").get("id").in(users));
        }

        if (states != null && !states.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> root.get("state").as(String.class).in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> root.get("category").get("id").in(categories));
        }

        return eventRepository.findAll(specification, page).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<EventShortDto> searchEventsByUser(String text, List<Long> categories, Boolean paid, // todo - переделать, особенно после изучения спецификаций
                                                  LocalDateTime startDate, LocalDateTime endDate,
                                                  Boolean onlyAvailable, String sort,
                                                  Integer from, Integer size, String ip, String uri) {

        saveInfoToStatistics(ip, uri);

        PageRequest page = PageRequest.of(from / size, size, Sort.by(
                sort != null && sort.equalsIgnoreCase("event_date") ? "event_date" : "id"));

        LocalDateTime startDateTime = Objects.requireNonNullElse(startDate, LocalDateTime.now());

        Specification<Event> specification = Specification.where(null);

        specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), EventStatus.PUBLISHED));

        specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("eventDate"), startDateTime));

        if (endDate != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("eventDate"), endDate));
        }

        if (text != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                            "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                            "%" + text.toLowerCase() + "%")
            ));
        }

        if (categories != null && !categories.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> root.get("category").get("id").in(categories));
        }

        if (onlyAvailable != null && onlyAvailable) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("participantLimit"), 0));
        }

        List<Event> events = eventRepository.findAll(specification, page);

        updateViewsOfEvents(events);

        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId,
                                           UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Событие с ID %s не найдено.", eventId)));

        if (!event.getState().equals(EventStatus.PENDING)) {
            throw new ConflictException("Статус события должен быть 'PENDING'");
        }

        String annotation = updateEventAdminRequest.getAnnotation();
        if (annotation != null && !annotation.isEmpty() && !annotation.isBlank()) {
            event.setAnnotation(annotation);
        }

        String title = updateEventAdminRequest.getTitle();
        if (title != null && !title.isEmpty() && !title.isBlank()) {
            event.setTitle(title);
        }

        Category category = CategoryMapper.toCategory(
                categoryService.readCategory(updateEventAdminRequest.getCategory()));
        if (category != null) {
            event.setCategory(category);
        }

        String description = updateEventAdminRequest.getDescription();
        if (description != null && !description.isEmpty() && !description.isBlank()) {
            event.setDescription(description);
        }

        LocalDateTime eventDate = updateEventAdminRequest.getEventDate();
        if (eventDate != null) {
            checkTimeBeforeEventStart(eventDate, HOURS_BEFORE_EVENT_ADMIN);
            event.setEventDate(eventDate);
        }

        Location location = LocationMapper.toLocation(updateEventAdminRequest.getLocation());
        if (location != null) {
            event.setLocation(location);
        }

        Integer participantLimit = updateEventAdminRequest.getParticipantLimit();
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }

        Boolean requestModeration = updateEventAdminRequest.getRequestModeration();
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }

        EventState eventState = updateEventAdminRequest.getStateAction();
        if (eventState != null) {
            switch (eventState) {
                case PUBLISH_EVENT:
                    event.setState(EventStatus.PUBLISHED);
                    break;
                case REJECT_EVENT:
                    event.setState(EventStatus.CANCELED);
                    break;
            }
        }

        Boolean paid = updateEventAdminRequest.getPaid();
        if (paid != null) {
            event.setPaid(paid);
        }

        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(Long userId,
                                          Long eventId,
                                          UpdateEventUserRequest updateEventUserRequest) {
        userService.readUser(userId);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Событие с ID %s не найдено.", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format("Пользователь с ID %s не создавал событие с ID %s.",
                    userId, eventId));
        }

        if (event.getState().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException("Статус события - 'PUBLISHED'. " +
                    "Редактировать можно только события со статусом 'PENDING' or 'CANCELED'");
        }

        String annotation = updateEventUserRequest.getAnnotation();
        if (annotation != null && !annotation.isEmpty() && !annotation.isBlank()) {
            event.setAnnotation(annotation);
        }

        String title = updateEventUserRequest.getTitle();
        if (title != null && !title.isEmpty() && !title.isBlank()) {
            event.setTitle(title);
        }

        Category category = CategoryMapper.toCategory(
                categoryService.readCategory(updateEventUserRequest.getCategory()));
        if (category != null) {
            event.setCategory(category);
        }

        String description = updateEventUserRequest.getDescription();
        if (description != null && !description.isEmpty() && !description.isBlank()) {
            event.setDescription(description);
        }

        LocalDateTime eventDate = updateEventUserRequest.getEventDate();
        if (eventDate != null) {
            checkTimeBeforeEventStart(eventDate, HOURS_BEFORE_EVENT_USER);
            event.setEventDate(eventDate);
        }

        Location location = LocationMapper.toLocation(updateEventUserRequest.getLocation());
        if (location != null) {
            event.setLocation(location);
        }

        Integer participantLimit = updateEventUserRequest.getParticipantLimit();
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }

        Boolean requestModeration = updateEventUserRequest.getRequestModeration();
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }

        EventState eventState = updateEventUserRequest.getStateAction();
        if (eventState != null) {
            switch (eventState) {
                case SEND_TO_REVIEW:
                    event.setState(EventStatus.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventStatus.CANCELED);
                    break;
            }
        }

        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toEventFullDto(updatedEvent);
    }

    private void checkTimeBeforeEventStart(LocalDateTime startDate, int minHours) {
        if (startDate.isBefore(LocalDateTime.now().plusHours(minHours))) {
            throw new WrongParameterException(String.format("До начала события менее %s ч.", minHours));
        }
    }

    private void saveInfoToStatistics(String ip, String uri) { // todo - статистика
        statClient.saveHit(HitDto.builder()
                .app("ewm-main-service")
                .ip(ip)
                .uri(uri)
                .timestamp(LocalDateTime.now())
                .build());
    }

    private void updateViewsOfEvents(List<Event> events) {  // todo - статистика
        List<String> uris = events.stream()
                .map(event -> String.format("/events/%s", event.getId()))
                .collect(Collectors.toList());

        List<StatDto> statistics = getViewsStatistics(uris);

        events.forEach(event -> {
            StatDto foundViewInStats = statistics.stream()
                    .filter(statDto -> {
                        Long eventIdFromStats = Long.parseLong(statDto.getUri().substring("/events/".length()));
                        return Objects.equals(eventIdFromStats, event.getId());
                    })
                    .findFirst()
                    .orElse(null);

            long currentCountViews = foundViewInStats == null ? 0 : foundViewInStats.getHits();
            event.setViews((int) currentCountViews + 1);
        });

        eventRepository.saveAll(events);
    }

    private List<StatDto> getViewsStatistics(List<String> uris) { // todo - статистика

        ResponseEntity<Object> stats = statClient.getStats(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2123, 12, 31, 23, 59, 59),
                true,
                uris);

        return (List<StatDto>) stats.getBody();
    }
}