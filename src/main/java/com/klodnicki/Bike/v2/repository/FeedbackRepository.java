package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Feedback entities.
 * This interface is a part of the Spring Data JPA repositories' infrastructure.
 * The Spring Data JPA will create a class that implements this interface automatically.
 * Then, the programmer can use the implemented interface to perform CRUD operations on Feedback entities.
 */
@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
