package com.todo.controllers;

import com.todo.contents.UserContent;
import com.todo.contents.UserHistoryContent;
import com.todo.contents.UserProfileContent;
import com.todo.mappers.UserMapper;
import com.todo.services.UserService;
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

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest extends JerseyTest {

  @Mock
  UserService userService;

  @Override
  public Application configure() {
    AbstractBinder binder = new AbstractBinder() {
      @Override
      protected void configure() {
        bind(userService).to(UserService.class);
      }
    };
    ResourceConfig config = new ResourceConfig(UserController.class);
    config.register(binder);
    return config;
  }

  @Test
  public void postAndGetTaskOkTest() {

    UserContent userContentForPost = new UserContent();
    UserProfileContent userProfileContent = new UserProfileContent();
    userProfileContent.setFirstName("John");
    userProfileContent.setLastName("Galt");
    userProfileContent.setAddress("Galt Valley");
    userProfileContent.setEmail("john.galt@dollar.com");
    userProfileContent.setPhoneNumber("666 666");
    userProfileContent.setUserName("john.galt");
    userContentForPost.setUserProfile(userProfileContent);
    UserHistoryContent userHistoryContent = new UserHistoryContent();
    userHistoryContent.setScore(0D);
    userContentForPost.setUserHistory(userHistoryContent);

    Entity<UserContent> userContentEntity = Entity.entity(userContentForPost,
        MediaType.APPLICATION_JSON);
    Response postResponse = target("user")
        .request()
        .post(userContentEntity);

    Assert.assertEquals(201, postResponse.getStatus());

    Mockito.when(userService.findUserByEmail(Mockito.anyString())).thenReturn(
        UserMapper.INSTANCE.contentToDomain(userContentForPost)
    );

    Response response = target("user")
        .queryParam("userEmail", userContentForPost.getUserProfile().getEmail()).request()
        .get();
    Assert.assertEquals(200, response.getStatus());
    UserContent userContentAfterPost = response.readEntity(UserContent.class);

    Assert.assertEquals(userContentForPost, userContentAfterPost);
  }
}
