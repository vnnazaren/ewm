package ru.practicum.mainservice.exceptions;

/**
 * Класс исключение "Некорректный параметр"
 */
public class WrongParameter extends RuntimeException {
    public WrongParameter(String message) {
        super(message);
    }
}