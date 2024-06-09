package ru.practicum.mainservice.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.util.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс класса-репозитория EVENT
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    Set<Event> findAllByIdIn(Set<Long> eventsSet);

    List<Event> findAllByInitiator(User user, PageRequest pageRequest);

    @Query("SELECT e " +    // todo - эту ёбань надо переделать по нормальному
            "FROM Event e " +
            "WHERE (e.initiator.id IN ?1 OR ?1 IS null) " +
            "AND (e.state IN ?2 OR ?2 IS null) " +
            "AND (e.category.id IN ?3 OR ?3 IS null) " +
            "AND (e.eventDate > ?4 OR ?4 IS null) " +
            "AND (e.eventDate < ?5 OR ?5 IS null) ")

    List<Event> searchEventsByAdmin(List<Long> users, List<Status> states, List<Long> categories,
                               LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT e " +   // todo - эту ёбань надо переделать по нормальному
            "FROM Event e " +
            "WHERE ((?1 IS null) OR ((lower(e.annotation) LIKE concat('%', lower(?1), '%')) OR (lower(e.description) LIKE concat('%', lower(?1), '%')))) " +
            "AND (e.category.id IN ?2 OR ?2 IS null) " +
            "AND (e.paid = ?3 OR ?3 IS null) " +
            "AND (e.eventDate > ?4 OR ?4 IS null) AND (e.eventDate < ?5 OR ?5 IS null) " +
            "AND (?6 = false OR ((?6 = true AND e.participantLimit > (SELECT count(*) FROM Request AS r WHERE e.id = r.event.id))) " +
            "OR (e.participantLimit > 0 )) ")
    List<Event> findAllEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                              LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Pageable pageable);

}