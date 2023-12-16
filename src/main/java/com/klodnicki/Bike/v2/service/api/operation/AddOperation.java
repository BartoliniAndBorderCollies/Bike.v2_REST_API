package com.klodnicki.Bike.v2.service.api.operation;

/**
 * Interface for an add operation.
 *
 * @param <T> the type of object that this interface returns.
 * @param <G> the type of object that this interface accepts.
 */
public interface AddOperation<T, G> {

    T add(G obj);
}



