package com.example.projectmanager.service;

import com.example.projectmanager.entity.Project;
import java.util.List;

public interface ProjectService {
    Project createProject(Project project);
    List<Project> getAllProjects();
}