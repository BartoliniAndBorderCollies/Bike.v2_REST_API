package com.klodnicki.Bike.v2.service;

public interface GenericService<T> {

    <T> T add();

    <T> T findById(Long id);

    <T> Iterable<T> findAll();

    <T> void deleteById(Long id);

    <T> T update(Long id);

}
