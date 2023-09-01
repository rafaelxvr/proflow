package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.infrastructure.model.Task;
import dev.ravel.proflow.infrastructure.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Project> getProjectsByClientId(int clientId) {
        return projectRepository.findByClientId(clientId);
    }

    @Override
    public List<Project> getProjects() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public void deleteProject(long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            List<Task> tasks = taskService.getTasksByProjectId((int) id);
            if (tasks != null) {
                tasks.forEach(task -> {
                    taskService.deleteTask((int)task.getId());
                });
            }
            projectRepository.deleteById(id);
        }
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }
}
