package com.klodnicki.Bike.v2.exception;

/**
 * A custom exception class that represents an exception when an object is not found in the database.
 * This class extends the Exception class, making it a checked exception.
 */
public class NotFoundInDatabaseException extends Exception {

    /**
     * The message that is used when the exception is thrown.
     * It is formatted to include the name of the class of the object that was not found.
     */
    private static final String MESSAGE = "%s object has not been found";

    /**
     * Constructs a new NotFoundInDatabaseException with the specified detail message.
     * The detail message is saved for later retrieval by the Throwable.getMessage() method.
     *
     * @param clazz the Class object of the object that was not found in the database.
     */
    public NotFoundInDatabaseException(Class<?> clazz) {
        super(String.format(MESSAGE, clazz.getName()));
    }
}
