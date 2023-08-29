package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.infrastructure.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }
}
