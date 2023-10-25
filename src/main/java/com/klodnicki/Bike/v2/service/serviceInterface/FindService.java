package com.klodnicki.Bike.v2.service.serviceInterface;

import java.util.List;

public interface FindService<T, ID> {

    T findById(ID id);

    List<T> findAll();
}
