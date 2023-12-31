package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ChargingStation entities.
 * This interface is a part of the Spring Data JPA repositories' infrastructure.
 * The Spring Data JPA will create a class that implements this interface automatically.
 * Then, the programmer can use the implemented interface to perform CRUD operations on ChargingStation entities.
 */
@Repository
public interface ChargingStationRepository extends CrudRepository<ChargingStation, Long> {
}
