package com.example.projectmanager.repository;

import com.example.projectmanager.entity.Task;
import com.example.projectmanager.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    List<TaskHistory> findByTaskOrderByCreatedAtDesc(Task task);
}
