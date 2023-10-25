package com.klodnicki.Bike.v2.service.serviceInterface;

public interface RentService<T, G> {
    T rent(G obj);
}
