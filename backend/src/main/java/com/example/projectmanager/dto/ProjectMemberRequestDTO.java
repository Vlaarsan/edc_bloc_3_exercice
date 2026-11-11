package com.example.projectmanager.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberRequestDTO {

    private String role;
    private Long projectId;
    private Long userId;
}
