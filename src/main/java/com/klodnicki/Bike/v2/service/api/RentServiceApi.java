package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;

public interface RentServiceApi<G, H, T, ID, ID2> {

    G rent(T obj) throws NotFoundInDatabaseException;

    H returnVehicle(ID id, ID2 id2) throws NotFoundInDatabaseException;
}

