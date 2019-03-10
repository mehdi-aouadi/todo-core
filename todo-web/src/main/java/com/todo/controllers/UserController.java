package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.AssignedProgramContent;
import com.todo.contents.UserContent;
import com.todo.mappers.AssignedProgramMapper;
import com.todo.mappers.UserMapper;
import com.todo.services.UserService;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

import lombok.NoArgsConstructor;

import java.util.UUID;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class UserController {

  private UserService userService;
  private UserMapper userMapper = UserMapper.INSTANCE;
  private AssignedProgramMapper assignedProgramMapper = AssignedProgramMapper.INSTANCE;
  private Gson jsonSerializer = new Gson();

  @Inject
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Path("details")
  public Response getUserByEmail(@QueryParam("email") String email) {
    return Response
        .status(OK)
        .entity(jsonSerializer.toJson(
            userMapper.contentToDomain(userService.findUserByEmail(email))))
        .build();
  }

  @GET
  @Path("exists")
  public Response checkUserExists(@QueryParam("email") String email) {
    return Response
        .status(OK)
        .entity(jsonSerializer.toJson(
            userService.userExists(email)))
        .build();
  }

  @GET
  @Path("assigned/programs")
  public Response getUserAssignedPrograms(
      @QueryParam("id") UUID id,
      @QueryParam("skip") int skip,
      @Max(100)
      @QueryParam("limit") int limit
  ) {
    return Response
        .status(OK)
        .entity(jsonSerializer.toJson(
            assignedProgramMapper.domainListToContentList(userService.findAssignedProgramsByUserId(id, skip, limit))))
        .build();
  }

  @POST
  @Path("assigned/programs")
  public Response addAssignedProgramToUser(
      @QueryParam("id") UUID id,
      AssignedProgramContent assignedProgramContent
  ) {
    userService.addAssignedProgram(assignedProgramMapper.contentToDomain(assignedProgramContent), id);
    return Response
        .status(CREATED)
        .build();
  }

  @POST
  public Response createNewUser(UserContent userContent) {
    userService.saveUser(userMapper.domainToContent(userContent));
    return Response.status(Response.Status.CREATED)
        .entity("User created successfully")
        .build();
  }
}
