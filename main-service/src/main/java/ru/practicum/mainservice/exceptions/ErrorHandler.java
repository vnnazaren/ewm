package ru.practicum.mainservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @ExceptionHandler({MissingRequestHeaderException.class, MethodArgumentNotValidException.class,
            ConstraintViolationException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(final Exception e) {
        log.error("{} - Status: {}, Description: {}, Timestamp: {}",
                "Bad Request", HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.name(), e.getMessage(),
                "Bad Request", LocalDateTime.now().format(FORMATTER)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInternalServerError(final Exception e) {
        log.error("{} - Status: {}, Description: {}, Timestamp: {}",
                "SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage(),
                "Internal Server Error", LocalDateTime.now().format(FORMATTER)), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({WrongParameter.class})
    public ResponseEntity<ErrorResponse> handleConflict(final Exception e) {
        log.error("{} - Status: {}, Description: {}, Timestamp: {}",
                "CONFLICT", HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.name(), e.getMessage(),
                "Data Integrity constraint violation occurred", LocalDateTime.now().format(FORMATTER)),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(final RuntimeException e) {
        log.error("{} - Status: {}, Description: {}, Timestamp: {}",
                "NOT FOUND", HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.name(), e.getMessage(),
                "Not Found", LocalDateTime.now().format(FORMATTER)), HttpStatus.NOT_FOUND);
    }


//
//    @ExceptionHandler({MissingRequestHeaderException.class,
//            MissingServletRequestParameterException.class,
//            ValidationException.class,
//            IllegalArgumentException.class})
//    public ResponseEntity<ErrorMessage> handleValidation(Exception e) {
//        log.info("HttpStatus.BAD_REQUEST (400). {}", e.getMessage());
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(new ErrorMessage(e.getMessage()));
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ErrorMessage> handleNotFoundException(EntityNotFoundException e) {
//        log.info("HttpStatus.NOT_FOUND (404). {}", e.getMessage());
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorMessage(e.getMessage()));
//    }
//
//    @ExceptionHandler(WrongParameter.class)
//    public ResponseEntity<ErrorMessage> handleWrongParameter(Exception e) {
//        log.info("HttpStatus.CONFLICT (409). {}", e.getMessage());
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(new ErrorMessage(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorMessage> handleThrowable(final Throwable t) {
//        log.info("HttpStatus.INTERNAL_SERVER_ERROR (500). {}", t.getMessage(), t);
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ErrorMessage(t.getMessage()));
//    }
}