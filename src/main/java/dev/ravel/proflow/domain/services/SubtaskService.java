package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Subtask;

import java.util.List;

public interface SubtaskService {
    Subtask addSubtask(Subtask subtask);

    Subtask getSubtaskById(String id);

    List<Subtask> getSubtasksByTaskId(int taskId);

    void deleteSubtask(String id);

    Subtask updateSubtask(Subtask subtask);
}
