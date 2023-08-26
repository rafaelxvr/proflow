package dev.ravel.proflow.presentation.controllers;

import dev.ravel.proflow.domain.services.ProjectService;
import dev.ravel.proflow.infrastructure.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        try {
            Project result = projectService.addProject(project);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Project added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add project");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
        }
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") long id) {
        Project result = projectService.getProjectById(id);

        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> result = projectService.getProjects();

        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/projects")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        try {
            Project result = projectService.updateProject(project);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a project.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
        }
    }
}