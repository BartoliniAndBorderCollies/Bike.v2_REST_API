package com.klodnicki.Bike.v2.service.api.operation;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;

import java.util.List;

/**
 * Interface for find operations.
 *
 * @param <T> the type of object that this interface returns.
 * @param <ID> the type of identifier that this interface accepts.
 */
public interface FindOperation<T, ID> {

    T findById(ID id) throws NotFoundInDatabaseException;

    List<T> findAll();
}
