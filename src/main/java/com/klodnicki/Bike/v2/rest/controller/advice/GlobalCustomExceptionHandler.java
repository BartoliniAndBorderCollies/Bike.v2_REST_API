package com.klodnicki.Bike.v2.rest.controller.advice;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalCustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Object[] detailMessageArguments = ex.getDetailMessageArguments();

        if (detailMessageArguments != null) {
            return new ResponseEntity<>("Something went wrong!: \n" + detailMessageArguments[1], HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Your request miss required data, check your request with validation requirements",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundInDatabaseException.class)
    public ResponseEntity<?> handleNotFoundInDatabaseException(NotFoundInDatabaseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
