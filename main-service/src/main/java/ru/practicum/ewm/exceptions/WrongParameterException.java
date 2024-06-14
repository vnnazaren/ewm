package ru.practicum.ewm.exceptions;

/**
 * Класс исключение "Некорректный параметр"
 */
public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
}