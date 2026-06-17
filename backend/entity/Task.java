package com.example.projectmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private String priority; // LOW / MEDIUM / HIGH
    private String status;   // TODO / IN_PROGRESS / DONE

    private LocalDate dueDate;
    private LocalDate endDate;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignedTo;
}