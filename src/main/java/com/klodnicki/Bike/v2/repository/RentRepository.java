package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Bike, Long> {

    Rent saveRent(User user, Bike bike, ChargingStation chargingStation);
}
