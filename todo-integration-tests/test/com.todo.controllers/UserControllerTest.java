package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.UserContent;
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
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest extends JerseyTest {

    @Mock
    UserService userService;

    private Gson jsonSerializer = new Gson();

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
        userContentForPost.setEmail("userfortest@email.com");
        userContentForPost.setId(UUID.randomUUID().toString());
        userContentForPost.setPhoneNumber("123456");

        Entity<UserContent> userContentEntity = Entity.entity(userContentForPost, MediaType.APPLICATION_JSON);
        Response postResponse = target("users")
                .request()
                .post(userContentEntity);

        Assert.assertEquals(201, postResponse.getStatus());

        Mockito.when(userService.findUserByEmail(Mockito.anyString())).thenReturn(UserMapper.INSTANCE.contentToUser(userContentForPost));

        Response response = target("users/details")
                .queryParam("email", userContentForPost.getEmail()).request()
                .get();
        Assert.assertEquals(200, response.getStatus());
        String responseString = response.readEntity(String.class);
        UserContent userContentAfterPost = jsonSerializer.fromJson(responseString,
                UserContent.class);

        Assert.assertEquals(userContentForPost.getEmail(), userContentAfterPost.getEmail());
        Assert.assertEquals(userContentForPost.getId(), userContentAfterPost.getId());
        Assert.assertEquals(userContentForPost.getPhoneNumber(), userContentAfterPost.getPhoneNumber());
    }
}
