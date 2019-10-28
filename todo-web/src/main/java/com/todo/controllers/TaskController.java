package com.todo.controllers;

import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.queries.TaskQuery;
import com.todo.services.TaskService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.OK;

@Path("task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class TaskController extends AbstractController {

  private TaskService taskService;
  private TaskMapper taskMapper = TaskMapper.INSTANCE;

  @Inject
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GET
  @Path("/list")
  public Response listTasks(@Context UriInfo uriInfo) {

    MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
    TaskQuery taskQuery = new TaskQuery(queryParameters);

    return Response.status(OK)
        .entity(taskMapper.domainToContent(
            taskService.findTasksByQuery((com.todo.repositories.impl.queries.TaskQuery) taskQuery.toDomainQuery())))
        .build();
  }

  @GET
  @Path("/{taskId}")
  public Response findTaskById(
      @PathParam("taskId")
      @Pattern(regexp = UUID_PATTERN, message = "Task Id must be a valid UUID.")
          UUID taskId
  ) {
    return Response.status(OK)
        .entity(taskMapper.domainToContent(
            taskService.findTaskById(taskId)))
        .build();
  }

  @POST
  @Path("/")
  public Response create(TaskContent taskContent) {
    TaskContent createdTask
        = taskMapper.domainToContent(
        taskService.createTask(taskMapper.contentToDomain(taskContent)));
    return Response.status(OK).entity(createdTask).build();
  }
}
