package dev.ravel.proflow.Services;

import dev.ravel.proflow.domain.services.TaskServiceImpl;
import dev.ravel.proflow.infrastructure.model.Task;
import dev.ravel.proflow.infrastructure.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void testAddTask() {
        Task newTask = new Task();
        newTask.setId(1);
        newTask.setName("Task 1");

        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        Task addedTask = taskService.addTask(newTask);

        assertNotNull(addedTask);
        assertEquals(1, addedTask.getId());
        assertEquals("Task 1", addedTask.getName());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(1, foundTask.getId());
        assertEquals("Task 1", foundTask.getName());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTasks() {
        List<Task> mockTaskList = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1);
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setId(2);
        task2.setName("Task 2");

        mockTaskList.add(task1);
        mockTaskList.add(task2);

        when(taskRepository.findAll()).thenReturn(mockTaskList);

        List<Task> tasks = taskService.getTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteTask() {
        long taskIdToDelete = 1L;

        taskService.deleteTask(taskIdToDelete);

        verify(taskRepository, times(1)).deleteById(taskIdToDelete);
    }

    @Test
    public void testUpdateTask() {
        Task taskToUpdate = new Task();
        taskToUpdate.setId(1);
        taskToUpdate.setName("Updated Task");

        when(taskRepository.save(any(Task.class))).thenReturn(taskToUpdate);

        Task updatedTask = taskService.updateTask(taskToUpdate);

        assertNotNull(updatedTask);
        assertEquals(1, updatedTask.getId());
        assertEquals("Updated Task", updatedTask.getName());

        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
