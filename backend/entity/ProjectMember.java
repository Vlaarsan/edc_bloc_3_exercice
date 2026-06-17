package com.example.projectmanager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role; // ADMIN / MEMBER / OBSERVER

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;
}