package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void shouldMapTotask() {
        //Given
        TaskDto taskDto = new TaskDto(12L, "test", "test_content");

        //When
        Task task = taskMapper.mapTotask(taskDto);

        //Then
        assertEquals("test", task.getTitle());
        assertEquals("test_content",task.getContent());
    }

    @Test
    public void shouldMapToTaskDto() {
        //Given
        Task task = new Task(12L, "test", "test_content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals("test", taskDto.getTitle());
        assertEquals("test_content",taskDto.getContent());
    }

    @Test
    public void shouldMapToTaskDtoList() {
        //Given
        Task task = new Task(12L, "test", "test_content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(1, taskDtos.size());
        assertEquals("test", taskDtos.get(0).getTitle());
        assertEquals("test_content",taskDtos.get(0).getContent());
    }
}