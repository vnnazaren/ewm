package ru.practicum.ewm.exceptions;

/**
 * Класс исключение "Сущность не найдена"
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}