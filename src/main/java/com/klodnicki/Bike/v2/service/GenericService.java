package com.klodnicki.Bike.v2.service;

public interface GenericService<T, G> {

    T add(G obj);

    T findById(Long id);

    Iterable<T> findAll();

    void deleteById(Long id);

    T update(Long id, G obj);

}
