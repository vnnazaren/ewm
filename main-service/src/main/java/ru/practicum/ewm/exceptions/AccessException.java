package ru.practicum.ewm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключение "Некорректный параметр"
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
}