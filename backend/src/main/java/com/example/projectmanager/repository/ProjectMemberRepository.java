package com.example.projectmanager.repository;

import com.example.projectmanager.entity.Project;
import com.example.projectmanager.entity.ProjectMember;
import com.example.projectmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProject(Project project);

    Optional<ProjectMember> findByProjectAndUser(Project project, User user);
}
