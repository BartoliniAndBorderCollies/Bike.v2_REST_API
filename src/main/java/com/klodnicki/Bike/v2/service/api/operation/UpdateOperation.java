package com.klodnicki.Bike.v2.service.api.operation;

public interface UpdateOperation<T, ID, G> {

    T update(ID id, G obj);
}
