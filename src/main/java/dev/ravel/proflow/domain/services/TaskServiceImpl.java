package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.infrastructure.model.Task;
import dev.ravel.proflow.infrastructure.repository.SubtaskRepository;
import dev.ravel.proflow.infrastructure.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final SubtaskService subtaskService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, SubtaskService subtaskService) {
        this.taskRepository = taskRepository;
        this.subtaskService = subtaskService;
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getTasksByProjectId(int projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> getTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteTask(int taskId) {
        Task task = taskRepository.findById((long)taskId).orElse(null);
        if (task != null) {
            List<Subtask> subtasks = subtaskService.getSubtasksByTaskId(taskId);
            if (subtasks != null) {
                subtasks.forEach(subtask -> {
                    subtaskService.deleteSubtask(subtask.getId());
                });
            }
            taskRepository.deleteById((long)taskId);
        }
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
}