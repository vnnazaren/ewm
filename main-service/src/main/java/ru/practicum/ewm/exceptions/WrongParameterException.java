package ru.practicum.ewm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключение "Некорректный параметр"
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
}