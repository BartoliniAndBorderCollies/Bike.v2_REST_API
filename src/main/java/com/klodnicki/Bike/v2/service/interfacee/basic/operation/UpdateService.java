package com.klodnicki.Bike.v2.service.interfacee.basic.operation;

public interface UpdateService<T, ID, G> {

    T update(ID id, G obj);
}
