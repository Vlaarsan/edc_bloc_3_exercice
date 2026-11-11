package com.example.projectmanager.service;

import com.example.projectmanager.dto.ProjectMemberDTO;
import com.example.projectmanager.dto.ProjectMemberRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberService {

    ProjectMemberDTO addMember(ProjectMemberRequestDTO request);

    List<ProjectMemberDTO> getMembersByProject(Long projectId);

    Optional<ProjectMemberDTO> getMember(Long projectId, Long userId);

    boolean removeMember(Long projectId, Long userId);
}
