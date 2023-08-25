package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

}
