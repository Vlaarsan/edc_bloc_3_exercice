package com.example.projectmanager.service;

import com.example.projectmanager.dto.TaskDTO;
import com.example.projectmanager.dto.TaskRequestDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskDTO createTask(TaskRequestDTO request);

    List<TaskDTO> getAllTasks();

    Optional<TaskDTO> getTaskById(Long id);

    List<TaskDTO> getTasksByProject(Long projectId);

    List<TaskDTO> getTasksAssignedTo(Long userId);

    TaskDTO updateTask(Long id, TaskRequestDTO request);

    boolean deleteTask(Long id);
}
