package com.example.projectmanager.controller;

import com.example.projectmanager.entity.Project;
import com.example.projectmanager.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Project> getAll() {
        return projectService.getAllProjects();
    }
}

@PostMapping
public Project create(@RequestBody ProjectDTO dto) {
    return projectService.createProject(dto);
}