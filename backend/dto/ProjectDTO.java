package com.example.projectmanager.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;

    private Long ownerId; // simplifié au lieu de renvoyer User complet
}