package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Compilation;

/**
 * Интерфейс класса-репозитория COMPILATION
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}