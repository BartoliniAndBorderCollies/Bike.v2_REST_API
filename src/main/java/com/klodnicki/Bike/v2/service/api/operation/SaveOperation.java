package com.klodnicki.Bike.v2.service.api.operation;

/**
 * Interface for a save operation.
 *
 * @param <T> the type of object that this interface accepts and returns.
 */
public interface SaveOperation<T> {
    T save(T obj);
}
