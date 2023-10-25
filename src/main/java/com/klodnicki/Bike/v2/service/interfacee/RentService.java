package com.klodnicki.Bike.v2.service.interfacee;

public interface RentService<T, G> {
    T rent(G obj);
}
