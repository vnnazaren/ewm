package ru.practicum.ewm.exceptions;

/**
 * Класс исключение "Валидация не прошла"
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}