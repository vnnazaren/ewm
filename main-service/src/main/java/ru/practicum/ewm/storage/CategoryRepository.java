package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Category;

/**
 * Интерфейс класса-репозитория CATEGORY
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}