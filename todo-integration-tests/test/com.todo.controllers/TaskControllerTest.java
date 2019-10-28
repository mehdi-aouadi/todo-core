package com.todo.controllers;

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
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest extends JerseyTest {

  @Mock
  TaskService taskServiceMock;

  @Override
  public Application configure() {
    AbstractBinder binder = new AbstractBinder() {
      @Override
      protected void configure() {
        bind(taskServiceMock).to(TaskService.class);
        bind(TaskServiceImpl.class).to(TaskService.class);
        bind(UserServiceImpl.class).to(UserService.class);
      }
    };
    ResourceConfig config = new ResourceConfig(TaskController.class);
    config.register(binder);
    return config;
  }

  @Test
  public void postAndGetTaskOkTest() {
    TaskContent taskContentForPost = new TaskContent();
    taskContentForPost.setName("Task for test");
    taskContentForPost.setSummary("Task Summary For Test.");
    taskContentForPost.setDescription("Task Description For Test.");
    taskContentForPost.setDuration(Duration.ofDays(20));
    taskContentForPost.setMediaIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
    taskContentForPost.setStartDate(LocalDateTime.now());

    Mockito.doReturn(TaskMapper.INSTANCE.contentToDomain(taskContentForPost)).when(taskServiceMock).createTask(Mockito.any(Task.class));
    Mockito.doReturn(TaskMapper.INSTANCE.contentToDomain(taskContentForPost)).when(taskServiceMock).findTaskById(Mockito.any(UUID.class));

    Entity<TaskContent> taskContentEntity = Entity.entity(taskContentForPost,
        MediaType.APPLICATION_JSON);
    Response postResponse = target("task")
        .request()
        .post(taskContentEntity);

    Assert.assertEquals(200, postResponse.getStatus());

    TaskContent createdTaskContent = postResponse.readEntity(TaskContent.class);

    Response response = target("task/" + UUID.randomUUID())
        .request()
        .get();
    Assert.assertEquals(200, response.getStatus());
    Assert.assertEquals(taskContentForPost, createdTaskContent);
  }
}
