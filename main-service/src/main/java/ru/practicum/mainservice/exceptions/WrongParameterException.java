package ru.practicum.mainservice.exceptions;

/**
 * Класс исключение "Некорректный параметр"
 */
public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
}