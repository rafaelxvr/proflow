package dev.ravel.proflow.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ravel.proflow.domain.services.TaskService;
import dev.ravel.proflow.infrastructure.model.Task;
import dev.ravel.proflow.presentation.controllers.TaskController;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testAddTask() throws Exception {
        Task taskToAdd = new Task();
        taskToAdd.setName("Task 1");
        taskToAdd.setDescription("Description");

        when(taskService.addTask((Task) any(Task.class))).thenReturn(taskToAdd);

        mockMvc.perform(post("/api/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskToAdd)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Task 1")))
                .andExpect((ResultMatcher) jsonPath("$.description", is("Description")));
    }

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task 1");
        task.setDescription("Description");

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/tasks/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Task 1")))
                .andExpect((ResultMatcher) jsonPath("$.description", is("Description")));
    }

    @Test
    public void testGetTasks() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setDescription("Description 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setDescription("Description 2");

        List<Task> taskList = Arrays.asList(task1, task2);

        when(taskService.getTasks()).thenReturn(taskList);

        mockMvc.perform(get("/api/tasks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Task 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Task 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");

        when(taskService.updateTask((Task) any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(post("/api/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Updated Task")))
                .andExpect((ResultMatcher) jsonPath("$.description", is("Updated Description")));
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/tasks/1"))
                .andExpect(status().isAccepted())
                .andExpect((ResultMatcher) content().string("Successfully deleted a task."));

        verify(taskService, times(1)).deleteTask(1L);
    }
}
