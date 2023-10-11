package com.klodnicki.Bike.v2.service;
import java.util.List;

public interface GenericService<T, G> {

    T add(G obj);

    T findById(Long id);

    List<T> findAll();

    void deleteById(Long id);

    T update(Long id, G obj);

}
