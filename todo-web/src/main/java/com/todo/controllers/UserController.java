package com.todo.controllers;

import com.todo.contents.UserContent;
import com.todo.mappers.AssignedProgramMapper;
import com.todo.mappers.UserMapper;
import com.todo.services.UserService;
import javax.inject.Inject;

import javax.validation.constraints.Max;
import java.util.UUID;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.NoArgsConstructor;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class UserController extends AbstractController {

  private UserService userService;
  private UserMapper userMapper = UserMapper.INSTANCE;
  private AssignedProgramMapper assignedProgramMapper = AssignedProgramMapper.INSTANCE;

  @Inject
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GET
  public Response getUserByEmail(
      @QueryParam("userEmail") String userEmail
  ) {
    return Response
        .status(OK)
        .entity(
            userMapper.domainToContent(userService.findUserByEmail(userEmail)))
        .build();
  }

  @GET
  @Path("/exists")
  public Response checkUserExists(
      @QueryParam("userEmail") String email
  ) {
    return Response
        .status(OK)
        .entity(
            userService.userExists(email))
        .build();
  }

  @GET
  @Path("/{userId}/assigned/program/{assignedProgramId}")
  public Response getUserAssignedProgram(
      @PathParam("userId")
      @Pattern(regexp = UUID_PATTERN, message = "User Id must be a valid UUID.")
          UUID userId,
      @PathParam("assignedProgramId")
      @Pattern(regexp = UUID_PATTERN, message = "AssignedProgram Id must be a valid UUID.")
          UUID assignedProgramId
  ) {
    return Response
        .status(OK)
        .entity(assignedProgramMapper
            .domainToContent(userService.findAssignedProgramById(userId, assignedProgramId))
        ).build();
  }

  @GET
  @Path("/{userId}/assigned/program/list")
  public Response getUserAssignedPrograms(
      @PathParam("userId")
      @Pattern(regexp = UUID_PATTERN, message = "User Id must be a valid UUID.")
          UUID userId,
      @QueryParam("skip") int skip,
      @Max(100)
      @QueryParam("limit") int limit
  ) {
    return Response
        .status(OK)
        .entity(assignedProgramMapper
            .domainListToContentList(userService.findAssignedProgramsByUserId(userId, skip, limit))
        ).build();
  }

  @POST
  @Path("/{userId}/assign/program/{programId}")
  public Response assignProgramToUser(
      @PathParam("userId")
      @Pattern(regexp = UUID_PATTERN, message = "User Id must be a valid UUID.")
          UUID userId,
      @PathParam("programId")
      @Pattern(regexp = UUID_PATTERN, message = "Program Id must be a valid UUID.")
          UUID programId
  ) {
    userService.addAssignedProgram(programId, userId);
    return Response
        .status(CREATED)
        .build();
  }

  @POST
  public Response createNewUser(UserContent userContent) {
    userService.createUser(userMapper.contentToDomain(userContent));
    return Response.status(Response.Status.CREATED)
        .entity("User created successfully")
        .build();
  }
}
