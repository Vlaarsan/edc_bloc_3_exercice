package com.example.projectmanager.service;

import com.example.projectmanager.dto.ProjectDTO;
import com.example.projectmanager.dto.ProjectRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    ProjectDTO createProject(ProjectRequestDTO request);

    List<ProjectDTO> getAllProjects();

    Optional<ProjectDTO> getProjectById(Long id);

    List<ProjectDTO> getProjectsByOwner(Long ownerId);

    ProjectDTO updateProject(Long id, ProjectRequestDTO request);

    boolean deleteProject(Long id);
}
