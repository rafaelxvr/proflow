package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.infrastructure.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByProjectId(@Param("id") int projectId);
}
