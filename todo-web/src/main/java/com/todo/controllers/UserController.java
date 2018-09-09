package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.UserContent;
import com.todo.mappers.UserMapper;
import com.todo.services.UserService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.NoArgsConstructor;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class UserController {

  private UserService userService;
  private UserMapper userMapper = UserMapper.INSTANCE;
  private Gson jsonSerializer = new Gson();

  @Inject
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Path("details")
  public Response getUserByEmail(@QueryParam("email") String email) {
    return Response
        .status(Response.Status.OK)
        .entity(jsonSerializer.toJson(
            userMapper.userToContent(userService.findUserByEmail(email))))
        .build();
  }

  @POST
  public Response createNewUser(UserContent userContent) {
    userService.saveUser(userMapper.contentToUser(userContent));
    return Response.status(Response.Status.CREATED)
        .entity("User created successfully")
        .build();
  }
}
