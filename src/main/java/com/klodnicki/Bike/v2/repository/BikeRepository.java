package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.Bike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends CrudRepository<Bike, Long> {

    List<Bike> findByIsRentedFalse();
}
