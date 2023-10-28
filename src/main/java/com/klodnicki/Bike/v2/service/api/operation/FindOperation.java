package com.klodnicki.Bike.v2.service.api.operation;

import java.util.List;

public interface FindOperation<T, ID> {

    T findById(ID id);

    List<T> findAll();
}
