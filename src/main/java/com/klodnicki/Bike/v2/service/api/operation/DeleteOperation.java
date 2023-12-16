package com.klodnicki.Bike.v2.service.api.operation;

/**
 * Interface for a delete operation.
 *
 * @param <ID> the type of identifier that this interface accepts.
 */
public interface DeleteOperation<ID> {

    void deleteById(ID id);
}
