package com.example.projectmanager.service;

import com.example.projectmanager.dto.TaskDTO;
import com.example.projectmanager.dto.TaskRequestDTO;
import com.example.projectmanager.entity.Project;
import com.example.projectmanager.entity.Task;
import com.example.projectmanager.entity.User;
import com.example.projectmanager.repository.ProjectRepository;
import com.example.projectmanager.repository.TaskRepository;
import com.example.projectmanager.repository.UserRepository;
import com.example.projectmanager.service.impl.TaskServiceImpl;
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
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_shouldSaveAndReturnDto() {
        TaskRequestDTO request = new TaskRequestDTO("Tâche A", "Desc", "HIGH", "TODO", null, null, 1L, null);

        Project project = new Project(); project.setId(1L); project.setName("Projet A");
        Task saved = new Task(); saved.setId(1L); saved.setTitle("Tâche A"); saved.setProject(project);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        TaskDTO result = taskService.createTask(request);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Tâche A");
        assertThat(result.getProjectId()).isEqualTo(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getAllTasks_shouldReturnList() {
        Task t1 = new Task(); t1.setId(1L); t1.setTitle("Tâche A");
        Task t2 = new Task(); t2.setId(2L); t2.setTitle("Tâche B");

        when(taskRepository.findAll()).thenReturn(List.of(t1, t2));

        List<TaskDTO> result = taskService.getAllTasks();

        assertThat(result).hasSize(2);
        verify(taskRepository).findAll();
    }

    @Test
    void getTasksByProject_shouldReturnFilteredList() {
        Project project = new Project(); project.setId(1L);
        Task t1 = new Task(); t1.setId(1L); t1.setTitle("Tâche A"); t1.setProject(project);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findByProject(project)).thenReturn(List.of(t1));

        List<TaskDTO> result = taskService.getTasksByProject(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Tâche A");
    }
}
