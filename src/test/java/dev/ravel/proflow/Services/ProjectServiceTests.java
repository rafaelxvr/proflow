package dev.ravel.proflow.Services;

import dev.ravel.proflow.domain.services.ProjectServiceImpl;
import dev.ravel.proflow.infrastructure.model.Project;
import dev.ravel.proflow.infrastructure.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    public void testAddProject() {
        Project newProject = new Project();
        newProject.setName("Project 1");
        newProject.setStatus("In Progress");

        when(projectRepository.save(any(Project.class))).thenReturn(newProject);

        Project addedProject = projectService.addProject(newProject);

        assertNotNull(addedProject);
        assertEquals("Project 1", addedProject.getName());
        assertEquals("In Progress", addedProject.getStatus());

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void testGetProjectById() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project 1");
        project.setStatus("In Progress");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project foundProject = projectService.getProjectById(1L);

        assertNotNull(foundProject);
        assertEquals("Project 1", foundProject.getName());
        assertEquals("In Progress", foundProject.getStatus());

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProjects() {
        List<Project> mockProjectList = new ArrayList<>();
        Project project1 = new Project();
        project1.setId(1L);
        project1.setName("Project 1");
        project1.setStatus("In Progress");

        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("Project 2");
        project2.setStatus("Completed");

        mockProjectList.add(project1);
        mockProjectList.add(project2);

        when(projectRepository.findAll()).thenReturn(mockProjectList);

        List<Project> projects = projectService.getProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteProject() {
        long projectIdToDelete = 1L;

        projectService.deleteProject(projectIdToDelete);

        verify(projectRepository, times(1)).deleteById(projectIdToDelete);
    }

    @Test
    public void testUpdateProject() {
        Project projectToUpdate = new Project();
        projectToUpdate.setId(1L);
        projectToUpdate.setName("Updated Project");
        projectToUpdate.setStatus("In Progress");

        when(projectRepository.save(any(Project.class))).thenReturn(projectToUpdate);

        Project updatedProject = projectService.updateProject(projectToUpdate);

        assertNotNull(updatedProject);
        assertEquals("Updated Project", updatedProject.getName());
        assertEquals("In Progress", updatedProject.getStatus());

        verify(projectRepository, times(1)).save(any(Project.class));
    }
}
