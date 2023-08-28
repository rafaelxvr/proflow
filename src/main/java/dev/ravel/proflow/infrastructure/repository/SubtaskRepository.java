package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, String> {
    List<Subtask> findByTaskId(int taskId);
}
