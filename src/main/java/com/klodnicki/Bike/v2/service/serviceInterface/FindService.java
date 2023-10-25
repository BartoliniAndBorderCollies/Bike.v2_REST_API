package com.klodnicki.Bike.v2.service.serviceInterface;

import java.util.List;

public interface FindService<T> {

    T findById(Long id);

    List<T> findAll();
}
