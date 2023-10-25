package com.klodnicki.Bike.v2.service.serviceInterface;

public interface UpdateService<T, G> {

    T update(Long id, G obj);
}
