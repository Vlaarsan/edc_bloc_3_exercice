package com.example.projectmanager.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    private String title;
    private String description;

    private String priority;
    private String status;

    private LocalDate dueDate;
    private LocalDate endDate;

    private Long projectId;
    private Long assignedToId;
}