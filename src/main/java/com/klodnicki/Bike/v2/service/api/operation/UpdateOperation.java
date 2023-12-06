package com.klodnicki.Bike.v2.service.api.operation;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;

public interface UpdateOperation<T, ID, G> {

    T update(ID id, G obj) throws NotFoundInDatabaseException;
}
