package com.example.projectmanager.repository;

import com.example.projectmanager.entity.Project;
import com.example.projectmanager.entity.Task;
import com.example.projectmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);

    Optional<Task> findByIdAndProject(Long id, Project project);

    List<Task> findByAssignedTo(User user);
}
