package com.klodnicki.Bike.v2.service.api.operation;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;

/**
 * Interface for an update operation.
 *
 * @param <T> the type of object that this interface returns.
 * @param <ID> the type of identifier that this interface accepts.
 * @param <G> the type of object that this interface accepts.
 */
public interface UpdateOperation<T, ID, G> {

    T update(ID id, G obj) throws NotFoundInDatabaseException;
}
