package dev.ravel.proflow.Controllers;

import dev.ravel.proflow.domain.services.TaskService;
import dev.ravel.proflow.infrastructure.model.Task;
import dev.ravel.proflow.presentation.controllers.TaskController;
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

class TaskControllerTests {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTask() {
        Task task = new Task();
        when(taskService.addTask(task)).thenReturn(task);

        ResponseEntity response = taskController.addTask(task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testGetTaskById() {
        long taskId = 1L;
        Task task = new Task();
        when(taskService.getTaskById(taskId)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testGetTasksByProjectId() {
        int projectId = 1;
        List<Task> tasks = Collections.singletonList(new Task());
        when(taskService.getTasksByProjectId(projectId)).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getTasksByProjectId(projectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = Collections.singletonList(new Task());
        when(taskService.getTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        when(taskService.updateTask(task)).thenReturn(task);

        ResponseEntity<Task> response = taskController.updateTask(task);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testDeleteTask() {
        long taskId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a task.");
        doNothing().when(taskService).deleteTask(taskId);

        ResponseEntity<String> response = taskController.deleteTask(taskId);

        assertEquals(expectedResponse, response);
    }
}
