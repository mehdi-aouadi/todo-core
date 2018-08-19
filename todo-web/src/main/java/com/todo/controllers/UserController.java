package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.UserContent;
import com.todo.mappers.UserMapper;
import com.todo.services.UserService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
