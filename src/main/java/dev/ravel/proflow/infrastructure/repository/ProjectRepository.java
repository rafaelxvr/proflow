package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.Client;
import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.infrastructure.model.Subtask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByClientId(long client_id);
}
