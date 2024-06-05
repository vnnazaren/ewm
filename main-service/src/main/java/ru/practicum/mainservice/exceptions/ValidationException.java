package ru.practicum.mainservice.exceptions;

/**
 * Класс исключение "Валидация не прошла"
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}