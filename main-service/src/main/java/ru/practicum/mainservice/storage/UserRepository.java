package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.User;

/**
 * Интерфейс класса-репозитория USER
 */
public interface UserRepository extends JpaRepository<User, Long> {
}