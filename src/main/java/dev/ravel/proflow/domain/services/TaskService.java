package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Task;

import java.util.List;

public interface TaskService {
    public Task addTask(Task task);
    public Task getTaskById(long id);
    public List<Task> getTasks();
    public void deleteTask(long id);
    public Task updateTask(Task task);
}
