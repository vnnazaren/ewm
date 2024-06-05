package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Request;

/**
 * Интерфейс класса-репозитория REQUEST
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
}