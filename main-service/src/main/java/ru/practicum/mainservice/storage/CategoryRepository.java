package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Category;

/**
 * Интерфейс класса-репозитория CATEGORY
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}