package com.klodnicki.Bike.v2.repository;

import com.klodnicki.Bike.v2.model.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface represents the repository for Authority.
 * It extends CrudRepository to provide CRUD operations for Authority.
 * @Repository: Indicates that this is a Repository class.
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
