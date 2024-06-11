package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Request;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.util.EventStatus;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс класса-репозитория REQUEST
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEvent(Event event);

    List<Request> findAllByRequester(User user);

    List<Request> findAllByIdInAndStatus(List<Long> ids, EventStatus status);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    List<Request> findByRequesterIdAndEventId(Long userId, Long eventId);
}