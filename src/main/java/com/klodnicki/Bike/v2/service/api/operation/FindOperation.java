package com.klodnicki.Bike.v2.service.api.operation;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;

import java.util.List;

public interface FindOperation<T, ID> {

    T findById(ID id) throws NotFoundInDatabaseException;

    List<T> findAll();
}
