package ru.practicum.mainservice.exceptions;

/**
 * Класс исключение "Некорректный параметр"
 */
public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}