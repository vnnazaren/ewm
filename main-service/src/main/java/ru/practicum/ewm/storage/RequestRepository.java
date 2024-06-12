package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.util.EventStatus;

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