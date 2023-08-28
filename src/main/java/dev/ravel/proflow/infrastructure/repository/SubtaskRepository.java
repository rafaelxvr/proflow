package dev.ravel.proflow.infrastructure.repository;

import dev.ravel.proflow.infrastructure.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, String> {
    @Query(value="SELECT st FROM Subtask st WHERE st.task.id = :id")
    List<Subtask> findByTaskId(@Param("id") int taskId);
}
