package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
