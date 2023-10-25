package com.klodnicki.Bike.v2.service.crudInterface;

public interface UpdateService<T, G> {

    T update(Long id, G obj);
}
