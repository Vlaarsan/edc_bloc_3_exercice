package com.example.projectmanager.service.impl;

import com.example.projectmanager.dto.ProjectMemberDTO;
import com.example.projectmanager.dto.ProjectMemberRequestDTO;
import com.example.projectmanager.entity.Project;
import com.example.projectmanager.entity.ProjectMember;
import com.example.projectmanager.entity.User;
import com.example.projectmanager.repository.ProjectMemberRepository;
import com.example.projectmanager.repository.ProjectRepository;
import com.example.projectmanager.repository.UserRepository;
import com.example.projectmanager.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectMemberDTO addMember(ProjectMemberRequestDTO request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectMember member = ProjectMember.builder()
                .role(request.getRole())
                .project(project)
                .user(user)
                .build();

        ProjectMember saved = projectMemberRepository.save(member);
        return toDto(saved);
    }

    @Override
    public List<ProjectMemberDTO> getMembersByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMemberRepository.findByProject(project).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectMemberDTO> getMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return projectMemberRepository.findByProjectAndUser(project, user).map(this::toDto);
    }

    @Override
    public boolean removeMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<ProjectMember> member = projectMemberRepository.findByProjectAndUser(project, user);
        if (member.isEmpty()) {
            return false;
        }
        projectMemberRepository.delete(member.get());
        return true;
    }

    private ProjectMemberDTO toDto(ProjectMember member) {
        return ProjectMemberDTO.builder()
                .id(member.getId())
                .role(member.getRole())
                .projectId(member.getProject().getId())
                .userId(member.getUser().getId())
                .build();
    }
}
