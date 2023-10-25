package com.klodnicki.Bike.v2.service.interfacee;

public interface UpdateService<T, ID, G> {

    T update(ID id, G obj);
}
