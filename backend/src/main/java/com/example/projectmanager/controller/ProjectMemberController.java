package com.example.projectmanager.controller;

import com.example.projectmanager.dto.ProjectMemberDTO;
import com.example.projectmanager.dto.ProjectMemberRequestDTO;
import com.example.projectmanager.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @PostMapping
    public ProjectMemberDTO addMember(@RequestBody ProjectMemberRequestDTO request) {
        return projectMemberService.addMember(request);
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectMemberDTO> getMembersByProject(@PathVariable Long projectId) {
        return projectMemberService.getMembersByProject(projectId);
    }

    @GetMapping("/{projectId}/user/{userId}")
    public ResponseEntity<ProjectMemberDTO> getMember(@PathVariable Long projectId,
                                                      @PathVariable Long userId) {
        return projectMemberService.getMember(projectId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{projectId}/user/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long projectId,
                                             @PathVariable Long userId) {
        boolean removed = projectMemberService.removeMember(projectId, userId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
