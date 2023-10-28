package com.klodnicki.Bike.v2.service.api;

public interface RentServiceApi<G, H, T, ID, ID2, ID3> {

    G rent(T obj);

    H returnVehicle(ID id, ID2 id2, ID3 id3);
}

