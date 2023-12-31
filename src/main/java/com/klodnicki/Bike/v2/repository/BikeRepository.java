package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.Bike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Bike entities.
 * This interface is a part of the Spring Data JPA repositories' infrastructure.
 * The Spring Data JPA will create a class that implements this interface automatically.
 * Then, the programmer can use the implemented interface to perform CRUD operations on Bike entities.
 */
@Repository
public interface BikeRepository extends CrudRepository<Bike, Long> {

    /**
     * Custom query method to find all bikes that are not rented.
     * Spring Data JPA will automatically generate the implementation based on the method name.
     *
     * @return List of Bike entities that are not currently rented.
     */
    List<Bike> findByIsRentedFalse();
}
