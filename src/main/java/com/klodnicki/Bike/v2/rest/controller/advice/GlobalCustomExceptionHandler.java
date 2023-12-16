package com.klodnicki.Bike.v2.rest.controller.advice;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler that handles exceptions across the whole application.
 */
@ControllerAdvice
public class GlobalCustomExceptionHandler {

    /**
     * Handles MethodArgumentNotValidException which occurs when a method argument annotated with @Valid fails validation.
     *
     * @param ex The exception that was thrown.
     * @return A ResponseEntity with a detailed error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Object[] detailMessageArguments = ex.getDetailMessageArguments();

        if (detailMessageArguments != null) {
            return new ResponseEntity<>("Something went wrong!: \n" + detailMessageArguments[1], HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Your request miss required data, check your request with validation requirements",
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles NotFoundInDatabaseException which occurs when an entity is not found in the database.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with the exception's message and a NOT_FOUND status.
     */
    @ExceptionHandler(NotFoundInDatabaseException.class)
    public ResponseEntity<?> handleNotFoundInDatabaseException(NotFoundInDatabaseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
