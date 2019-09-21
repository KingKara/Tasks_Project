package com.crud.tasks.service;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Autowired
    private TrelloService trelloService;

    @Test
    public void shouldGetAllTasks() {
        //Given
        Task task = new Task("test", "test");
        dbService.saveTask(task);
        long id = task.getId();

        //When
        List<Task> tasks = dbService.getAllTasks();
        int checkItem = tasks.size() - 1;

        //Then
        assertEquals("test", tasks.get(checkItem).getTitle());
        assertTrue(tasks.size() > 0);

        //CleanUp
        dbService.deleteTask(id);
    }

    @Test
    public void shouldGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task("test", "test");
        dbService.saveTask(task);
        long id = task.getId();

        //When
        Task taskFromGetMethod = dbService.getTask(id).orElseThrow(TaskNotFoundException::new);

        //Then
        assertEquals("test", taskFromGetMethod.getTitle());

        //CleanUp
        dbService.deleteTask(id);
    }
}
