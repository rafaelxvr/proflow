package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.infrastructure.model.Subtask;

import java.util.List;

public interface ProjectService {
    Project addProject(Project project);
    Project getProjectById(long id);
    List<Project> getProjectsByClientId(int clientId);
    List<Project> getProjects();
    void deleteProject(long id);
    Project updateProject(Project project);
}
