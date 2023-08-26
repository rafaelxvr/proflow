package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.infrastructure.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
