package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
