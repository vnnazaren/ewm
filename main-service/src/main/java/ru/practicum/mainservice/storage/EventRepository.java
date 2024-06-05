package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Event;

/**
 * Интерфейс класса-репозитория EVENT
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}