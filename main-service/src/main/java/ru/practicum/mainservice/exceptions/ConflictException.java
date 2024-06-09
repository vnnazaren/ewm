package ru.practicum.mainservice.exceptions;

/**
 * Класс исключение "Некорректный параметр"
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}