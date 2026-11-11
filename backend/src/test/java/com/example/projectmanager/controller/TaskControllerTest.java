package com.example.projectmanager.controller;

import com.example.projectmanager.dto.TaskDTO;
import com.example.projectmanager.dto.TaskRequestDTO;
import com.example.projectmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        TaskDTO dto = new TaskDTO(1L, "Tâche A", "Desc", "HIGH", "TODO", null, null, 1L, null);
        when(taskService.createTask(any(TaskRequestDTO.class))).thenReturn(dto);

        String json = """
            {"title":"Tâche A","description":"Desc","priority":"HIGH","status":"TODO","projectId":1}
            """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tâche A"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    void getAllTasks_shouldReturnList() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of(
                new TaskDTO(1L, "Tâche A", "Desc", "HIGH", "TODO", null, null, 1L, null),
                new TaskDTO(2L, "Tâche B", "Desc", "LOW", "DONE", null, null, 1L, null)
        ));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Tâche A"))
                .andExpect(jsonPath("$[1].status").value("DONE"));
    }

    @Test
    void getTasksByProject_shouldReturnFilteredList() throws Exception {
        when(taskService.getTasksByProject(1L)).thenReturn(List.of(
                new TaskDTO(1L, "Tâche A", "Desc", "HIGH", "TODO", null, null, 1L, null)
        ));

        mockMvc.perform(get("/tasks/project/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectId").value(1));
    }
}
