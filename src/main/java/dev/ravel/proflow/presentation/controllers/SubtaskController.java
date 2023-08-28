package dev.ravel.proflow.presentation.controllers;

import dev.ravel.proflow.domain.services.SubtaskService;
import dev.ravel.proflow.infrastructure.model.Subtask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SubtaskController {
    private final SubtaskService subtaskService;

    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSubtask(@RequestBody Subtask subtask) {
        try {
            Subtask result = subtaskService.addSubtask(subtask);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Subtask added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add subtask");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
        }
    }

    @GetMapping("/subtasks/{id}")
    public ResponseEntity<Subtask> getSubtaskById(@PathVariable("id") String id) {
        try {
            Subtask result = subtaskService.getSubtaskById(id);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<Subtask>> getSubtasksByTaskId(@PathVariable("taskId") int taskId) {
        try {
            List<Subtask> result = subtaskService.getSubtasksByTaskId(taskId);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/subtasks")
    public ResponseEntity<Subtask> updateSubtask(@RequestBody Subtask subtask) {
        try {
            Subtask result = subtaskService.updateSubtask(subtask);

            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/subtasks/{id}")
    public ResponseEntity<String> deleteSubtask(@PathVariable("id") String id) {
        try {
            subtaskService.deleteSubtask(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a subtask.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
        }
    }
}
