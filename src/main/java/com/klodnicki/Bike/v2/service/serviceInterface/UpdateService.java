package com.klodnicki.Bike.v2.service.serviceInterface;

public interface UpdateService<T, ID, G> {

    T update(ID id, G obj);
}
