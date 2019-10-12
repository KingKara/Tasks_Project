package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetEmptyList() throws Exception {
        //Given
        List<TaskDto> list = new ArrayList<>();
        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(list);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        TaskDto task1 = new TaskDto("Test title", "Test content");
        TaskDto task2 = new TaskDto("Test title 2", "Test content 2");
        List<TaskDto> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(list);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Test title")))
                .andExpect(jsonPath("$[0].content", is("Test content")))
                .andExpect(jsonPath("$[1].title", is("Test title 2")))
                .andExpect(jsonPath("$[1].content", is("Test content 2")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        TaskDto task = new TaskDto(123L, "Test title", "Test content");
        Long id = task.getId();
        System.out.println(id);

        when(taskMapper.mapToTaskDto(dbService.getTask(id))).thenReturn(task);

        //When & Then
        mockMvc.perform(get("/v1/tasks/"+id).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("taskId", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test title")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto task = new TaskDto("Test title", "Test content");
        Task createdTask = new Task("Test title", "Test content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        when(dbService.saveTask(taskMapper.mapTotask(ArgumentMatchers.any(TaskDto.class)))).thenReturn(createdTask);

        //When & Then
        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(dbService).saveTask(taskMapper.mapTotask(ArgumentMatchers.any(TaskDto.class)));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto task = new TaskDto("Test title", "Test content");
        TaskDto updatedTask = new TaskDto("Test update", "Test update");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapTotask(task)))).thenReturn(updatedTask);

        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("title", is("Test update")))
                .andExpect(jsonPath("content", is("Test update")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        TaskDto task = new TaskDto(123L, "Test title", "Test content");
        Task taskFromGet = new Task(123L, "Test title", "Test content");
        Long id = task.getId();

        when(dbService.getTask(id)).thenReturn(taskFromGet);
        when(dbService.saveTask(taskMapper.mapTotask(ArgumentMatchers.any(TaskDto.class)))).thenReturn(taskFromGet);

        //When & Then
        mockMvc.perform(delete("/v1/tasks/"+id).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("taskId", id.toString()))
                .andExpect(status().isOk());
        verify(dbService).deleteTask(id);

    }
}