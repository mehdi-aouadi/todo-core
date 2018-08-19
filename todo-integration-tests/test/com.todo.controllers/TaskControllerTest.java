package com.todo.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.model.Task;
import com.todo.services.TaskService;
import com.todo.services.UserService;
import com.todo.services.impl.TaskServiceImpl;
import com.todo.services.impl.UserServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest extends JerseyTest {

    @Mock
    TaskService taskServiceMock;

    private Gson jsonSerializer = new Gson();

    @Override
    public Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(taskServiceMock).to(TaskService.class);
//                bind(TaskServiceImpl.class).to(TaskService.class);
//                bind(UserServiceImpl.class).to(UserService.class);
            }
        };
        ResourceConfig config = new ResourceConfig(TaskController.class);
        config.register(binder);
        return config;
    }

    @Test
    public void postAndGetTaskOkTest() {
        Task taskForTest = new Task();
        taskForTest.setSummary("Task for integration test");
        taskForTest.setDescription("assignee@test.com");
        Mockito.when(taskServiceMock.getAllUserAssignedTasksByEmail(Mockito.anyString()))
                .thenReturn(Arrays.asList(taskForTest));

        TaskContent taskContentForPost = new TaskContent();
        taskContentForPost.setAssigneeEmail("assignee@email.com");
        taskContentForPost.setRequesterEmail("requester@email.com");
        taskContentForPost.setSummary("Task Summary For Test.");
        taskContentForPost.setDescription("Task Description For Test.");
        taskContentForPost.setWishDate(LocalDateTime.now().plusDays(2));
        taskContentForPost.setId(UUID.randomUUID().toString());

        Entity<TaskContent> taskContentEntity = Entity.entity(taskContentForPost, MediaType.APPLICATION_JSON);
        Response postResponse = target("tasks")
                .request()
                .post(taskContentEntity);

        Assert.assertEquals(200, postResponse.getStatus());

        Response response = target("tasks/assigned")
                .queryParam("email", taskContentForPost.getAssigneeEmail()).request()
                .get();
        Assert.assertEquals(200, response.getStatus());
        List<TaskContent> taskContentListAfterPost = jsonSerializer.fromJson(response.readEntity(String.class),
                new TypeToken<List<TaskContent>>(){}.getType());

        Assert.assertEquals(1, taskContentListAfterPost.size());
        Assert.assertTrue(taskContentListAfterPost.contains(TaskMapper.INSTANCE.taskToContent(taskForTest)));
    }
}
