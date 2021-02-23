package com.todo.controllers;

import com.todo.common.Page;
import com.todo.contents.UserContent;
import com.todo.mappers.AssignedProgramMapper;
import com.todo.mappers.AssignedProgramMapperDecorator;
import com.todo.mappers.UserMapper;
import com.todo.model.AssignedProgram;
import com.todo.model.User;
import com.todo.queries.AssignedProgramQuery;
import com.todo.services.AssignedProgramService;
import com.todo.services.UserService;
import com.todo.services.impl.ProgramServiceImpl;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.*;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class UserController extends AbstractController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private UserService userService;
  private AssignedProgramService assignedProgramService;

  @Inject
  public UserController(UserService userService,
                        AssignedProgramService assignedProgramService) {
    this.userService = userService;
    this.assignedProgramService = assignedProgramService;
  }

  @GET
  public Response getUserByEmail(
      @QueryParam("userEmail") String userEmail
  ) {
    Optional<User> user = userService.findByEmail(userEmail);
    if(user.isPresent()) {
      return Response
              .status(OK)
              .entity(userMapper().domainToContent(user.get()))
              .build();
    } else {
      return Response.status(NOT_FOUND).build();
    }
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

  @POST
  public Response createNewUser(UserContent userContent) {
    userService.insert(userMapper().contentToDomain(userContent));
    return Response.status(Response.Status.CREATED)
            .entity("User created successfully")
            .build();
  }

  @GET
  @Path("/assigned/program/{assignedProgramId}")
  public Response getUserAssignedProgram(
          @PathParam("assignedProgramId")
          @Pattern(regexp = UUID_PATTERN, message = "AssignedProgram Id must be a valid UUID.")
              UUID assignedProgramId
      ) {
    return assignedProgramService.findById(assignedProgramId)
            .map(assignedProgram
                    -> Response.status(OK).entity(assignedProgramMapper().domainToContent(assignedProgram))
                    .build()
            ).orElse(Response.status(NOT_FOUND).build());
  }

  @GET
  @Path("/{userId}/assigned/program/list")
  public Response getUserAssignedPrograms(
      @PathParam("userId")
      @Pattern(regexp = UUID_PATTERN, message = "User Id must be a valid UUID.")
          UUID userId,
      @Context UriInfo uriInfo) {
    AssignedProgramQuery assignedProgramQuery
        = new AssignedProgramQuery(uriInfo.getQueryParameters(), userId);
    if(assignedProgramQuery.isValid()) {
      Page<AssignedProgram> assignedProgramPage = assignedProgramService.find(assignedProgramQuery.toDomainQuery());
      return Response
              .status(OK)
              .entity(assignedProgramMapper().domainPageToContentPage(assignedProgramPage))
              .build();
    } else {
      LOGGER.error("Invalid parameters on call to /user/{userId}/assigned/program/list : {}. Detailed error : {}",
              userId.toString(),
              assignedProgramQuery.errorMessage());
      return Response.status(BAD_REQUEST).build();
    }

  }

  @POST
  @Path("/{userId}/assign/program/{programId}")
  public Response assignProgramToUser(
      @PathParam("userId")
      @Pattern(regexp = UUID_PATTERN, message = "User Id must be a valid UUID.")
          UUID userId,
      @PathParam("programId")
      @Pattern(regexp = UUID_PATTERN, message = "Program Id must be a valid UUID.")
          UUID programId) {
    userService.enrollProgram(userId, programId);
    return Response
        .status(CREATED)
        .build();
  }

  private AssignedProgramMapperDecorator assignedProgramMapper() {
    return new AssignedProgramMapperDecorator();
  }
  private UserMapper userMapper() {
    return UserMapper.INSTANCE;
  }
}
