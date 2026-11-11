package com.example.projectmanager.service;

import com.example.projectmanager.dto.ProjectDTO;
import com.example.projectmanager.dto.ProjectRequestDTO;
import com.example.projectmanager.entity.Project;
import com.example.projectmanager.entity.User;
import com.example.projectmanager.repository.ProjectRepository;
import com.example.projectmanager.repository.UserRepository;
import com.example.projectmanager.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void createProject_shouldSaveAndReturnDto() {
        ProjectRequestDTO request = new ProjectRequestDTO("Projet A", "Description", null, 1L);

        User owner = new User(); owner.setId(1L); owner.setUsername("alice"); owner.setEmail("a@test.com");
        Project saved = new Project(); saved.setId(1L); saved.setName("Projet A"); saved.setOwner(owner);

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(projectRepository.save(any(Project.class))).thenReturn(saved);

        ProjectDTO result = projectService.createProject(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Projet A");
        assertThat(result.getOwnerId()).isEqualTo(1L);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void getAllProjects_shouldReturnList() {
        Project p1 = new Project(); p1.setId(1L); p1.setName("Projet A");
        Project p2 = new Project(); p2.setId(2L); p2.setName("Projet B");

        when(projectRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProjectDTO> result = projectService.getAllProjects();

        assertThat(result).hasSize(2);
        verify(projectRepository).findAll();
    }

    @Test
    void getProjectById_shouldReturnDtoWhenFound() {
        Project p = new Project(); p.setId(1L); p.setName("Projet A");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<ProjectDTO> result = projectService.getProjectById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Projet A");
    }

    @Test
    void deleteProject_shouldReturnTrueWhenDeleted() {
        when(projectRepository.existsById(1L)).thenReturn(true);

        boolean result = projectService.deleteProject(1L);

        assertThat(result).isTrue();
        verify(projectRepository).deleteById(1L);
    }
}
