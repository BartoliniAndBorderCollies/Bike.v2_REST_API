package com.klodnicki.Bike.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Object[] detailMessageArguments = ex.getDetailMessageArguments();

        if (detailMessageArguments != null) {
            return new ResponseEntity<>("Something went wrong!: \n" + detailMessageArguments[1], HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Your request miss required data, check your request with validation requirements",
                HttpStatus.BAD_REQUEST);
    }
}
