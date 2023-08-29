package dev.ravel.proflow.Controllers;

import dev.ravel.proflow.domain.services.ProjectService;
import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.presentation.controllers.ProjectController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectControllerTests {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProject() {
        Project project = new Project();
        when(projectService.addProject(project)).thenReturn(project);

        ResponseEntity<Project> response = projectController.addProject(project);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    void testGetProjectById() {
        long projectId = 1L;
        Project project = new Project();
        when(projectService.getProjectById(projectId)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    void testGetProjectsByClientId() {
        int clientId = 1;
        List<Project> projects = Collections.singletonList(new Project());
        when(projectService.getProjectsByClientId(clientId)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getProjectsByClientId(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void testGetProjects() {
        List<Project> projects = Collections.singletonList(new Project());
        when(projectService.getProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void testUpdateProject() {
        Project project = new Project();
        when(projectService.updateProject(project)).thenReturn(project);

        ResponseEntity<Project> response = projectController.updateProject(project);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    void testDeleteProject() {
        long projectId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a project.");
        doNothing().when(projectService).deleteProject(projectId);

        ResponseEntity<String> response = projectController.deleteProject(projectId);

        assertEquals(expectedResponse, response);
    }
}