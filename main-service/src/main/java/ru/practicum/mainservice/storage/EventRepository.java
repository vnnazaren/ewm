package ru.practicum.mainservice.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.util.EventStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс класса-репозитория EVENT
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAll(Specification<Event> spec, Pageable pageable);

    Set<Event> findAllByIdIn(Set<Long> eventsSet);

    Optional<Event> findByIdAndState(Long eventId, EventStatus status);

    List<Event> findAllByInitiator(User user, PageRequest pageRequest);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);
}