package com.example.projectmanager.controller;

import com.example.projectmanager.dto.ProjectDTO;
import com.example.projectmanager.dto.ProjectRequestDTO;
import com.example.projectmanager.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void createProject_shouldReturnCreatedProject() throws Exception {
        ProjectDTO dto = new ProjectDTO(1L, "Projet A", "Desc", null, 1L);
        when(projectService.createProject(any(ProjectRequestDTO.class))).thenReturn(dto);

        String json = """
            {"name":"Projet A","description":"Desc","ownerId":1}
            """;

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Projet A"))
                .andExpect(jsonPath("$.ownerId").value(1));
    }

    @Test
    void getAllProjects_shouldReturnList() throws Exception {
        when(projectService.getAllProjects()).thenReturn(List.of(
                new ProjectDTO(1L, "Projet A", "Desc", null, 1L),
                new ProjectDTO(2L, "Projet B", "Desc", null, 2L)
        ));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Projet A"))
                .andExpect(jsonPath("$[1].name").value("Projet B"));
    }

    @Test
    void getProjectById_shouldReturn404WhenNotFound() throws Exception {
        when(projectService.getProjectById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/projects/99"))
                .andExpect(status().isNotFound());
    }
}
