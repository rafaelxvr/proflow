package dev.ravel.proflow.Controllers;

import dev.ravel.proflow.domain.services.SubtaskService;
import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.presentation.controllers.SubtaskController;
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

class SubtaskControllerTests {

    @Mock
    private SubtaskService subtaskService;

    @InjectMocks
    private SubtaskController subtaskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubtask() {
        Subtask subtask = new Subtask();
        when(subtaskService.addSubtask(subtask)).thenReturn(subtask);

        ResponseEntity<String> response = subtaskController.addSubtask(subtask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Subtask added successfully", response.getBody());
    }

    @Test
    void testGetSubtaskById() {
        String subtaskId = "123";
        Subtask subtask = new Subtask();
        when(subtaskService.getSubtaskById(subtaskId)).thenReturn(subtask);

        ResponseEntity<Subtask> response = subtaskController.getSubtaskById(subtaskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subtask, response.getBody());
    }

    @Test
    void testGetSubtasksByTaskId() {
        int taskId = 1;
        List<Subtask> subtasks = Collections.singletonList(new Subtask());
        when(subtaskService.getSubtasksByTaskId(taskId)).thenReturn(subtasks);

        ResponseEntity<List<Subtask>> response = subtaskController.getSubtasksByTaskId(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subtasks, response.getBody());
    }

    @Test
    void testUpdateSubtask() {
        Subtask subtask = new Subtask();
        when(subtaskService.updateSubtask(subtask)).thenReturn(subtask);

        ResponseEntity<Subtask> response = subtaskController.updateSubtask(subtask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subtask, response.getBody());
    }

    @Test
    void testDeleteSubtask() {
        String subtaskId = "123";
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a subtask.");
        doNothing().when(subtaskService).deleteSubtask(subtaskId);

        ResponseEntity<String> response = subtaskController.deleteSubtask(subtaskId);

        assertEquals(expectedResponse, response);
    }
}