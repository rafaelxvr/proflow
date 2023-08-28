package dev.ravel.proflow;

import dev.ravel.proflow.domain.services.SubtaskServiceImpl;
import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.infrastructure.repository.SubtaskRepository;
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
public class SubtaskServiceTests {

    @Mock
    private SubtaskRepository subtaskRepository;

    @InjectMocks
    private SubtaskServiceImpl subtaskService;

    @Test
    public void testAddSubtask() {
        Subtask newSubtask = new Subtask();
        newSubtask.setId("1");
        newSubtask.setName("Subtask 1");

        when(subtaskRepository.save(any(Subtask.class))).thenReturn(newSubtask);

        Subtask addedSubtask = subtaskService.addSubtask(newSubtask);

        assertNotNull(addedSubtask);
        assertEquals("1", addedSubtask.getId());
        assertEquals("Subtask 1", addedSubtask.getName());

        verify(subtaskRepository, times(1)).save(any(Subtask.class));
    }

    @Test
    public void testGetSubtaskById() {
        Subtask subtask = new Subtask();
        subtask.setId("1");
        subtask.setName("Subtask 1");

        when(subtaskRepository.findById("1")).thenReturn(Optional.of(subtask));

        Subtask foundSubtask = subtaskService.getSubtaskById("1");

        assertNotNull(foundSubtask);
        assertEquals("1", foundSubtask.getId());
        assertEquals("Subtask 1", foundSubtask.getName());

        verify(subtaskRepository, times(1)).findById("1");
    }

    @Test
    public void testGetSubtasksByTaskId() {
        int taskId = 1;

        List<Subtask> mockSubtaskList = new ArrayList<>();
        Subtask subtask1 = new Subtask();
        subtask1.setId("1");
        subtask1.setName("Subtask 1");

        Subtask subtask2 = new Subtask();
        subtask2.setId("2");
        subtask2.setName("Subtask 2");

        mockSubtaskList.add(subtask1);
        mockSubtaskList.add(subtask2);

        when(subtaskRepository.findByTaskId(taskId)).thenReturn(mockSubtaskList);

        List<Subtask> subtasks = subtaskService.getSubtasksByTaskId(taskId);

        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());

        verify(subtaskRepository, times(1)).findByTaskId(taskId);
    }

    @Test
    public void testDeleteSubtask() {
        String subtaskIdToDelete = "1";

        subtaskService.deleteSubtask(subtaskIdToDelete);

        verify(subtaskRepository, times(1)).deleteById(subtaskIdToDelete);
    }

    @Test
    public void testUpdateSubtask() {
        Subtask subtaskToUpdate = new Subtask();
        subtaskToUpdate.setId("1");
        subtaskToUpdate.setName("Updated Subtask");

        when(subtaskRepository.save(any(Subtask.class))).thenReturn(subtaskToUpdate);

        Subtask updatedSubtask = subtaskService.updateSubtask(subtaskToUpdate);

        assertNotNull(updatedSubtask);
        assertEquals("1", updatedSubtask.getId());
        assertEquals("Updated Subtask", updatedSubtask.getName());

        verify(subtaskRepository, times(1)).save(any(Subtask.class));
    }
}