package com.klodnicki.Bike.v2.service.interfacee;

public interface RentService<G, H, T, ID, ID2, ID3> {

    G rent(T obj);

    H returnVehicle(ID id, ID2 id2, ID3 id3);
}

