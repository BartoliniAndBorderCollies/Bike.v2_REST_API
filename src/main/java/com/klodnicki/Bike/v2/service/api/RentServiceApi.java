package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;

/**
 * This interface defines the operations for the RentService.
 * It is a generic interface where G, H, T, ID, and ID2 are type parameters.
 *
 * @param <G> This represents the return type of the rent method.
 * @param <H> This represents the return type of the returnVehicle method.
 * @param <T> This represents the type of object to be rented.
 * @param <ID> This represents the type of ID used in the returnVehicle method.
 * @param <ID2> This represents the second type of ID used in the returnVehicle method.
 */
public interface RentServiceApi<G, H, T, ID, ID2> {

    /**
     * This method is used to rent an object.
     * @param obj This is the object to be rented.
     * @return G This returns the result of the rent operation.
     * @throws NotFoundInDatabaseException If no bike, user, or charging station with the given IDs is found.
     */
    G rent(T obj) throws NotFoundInDatabaseException;

    /**
     * This method is used to return a rented vehicle.
     * @param id This is the ID related to the rent operation.
     * @param id2 This is the second ID related to the return operation.
     * @return H This returns the result of the return operation.
     */
    H returnVehicle(ID id, ID2 id2) throws NotFoundInDatabaseException;
}

