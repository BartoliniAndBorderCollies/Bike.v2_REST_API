package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 * This interface is a part of the Spring Data JPA repositories' infrastructure.
 * The Spring Data JPA will create a class that implements this interface automatically.
 * Then, the programmer can use the implemented interface to perform CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);
}
