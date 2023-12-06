package com.klodnicki.Bike.v2.exception;

public class NotFoundInDatabaseException extends Exception {

    private static final String MESSAGE = "%s object has not been found";

    public NotFoundInDatabaseException(Class<?> clazz) {
        super(String.format(MESSAGE, clazz.getName()));
    }
}
