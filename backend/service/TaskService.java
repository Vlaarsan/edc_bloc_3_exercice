package com.example.projectmanager.service;

import com.example.projectmanager.entity.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getAllTasks();
}