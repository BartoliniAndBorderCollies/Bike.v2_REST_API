package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
