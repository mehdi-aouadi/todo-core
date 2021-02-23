package com.todo.controllers;

import com.todo.common.Page;
import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.mappers.TaskMapperDecorator;
import com.todo.model.Task;
import com.todo.queries.TaskQuery;
import com.todo.services.TaskService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.*;

@Path("task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class TaskController extends AbstractController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private TaskService taskService;

  @Inject
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GET
  @Path("/list")
  public Response listTasks(@Context UriInfo uriInfo) {

    MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
    TaskQuery taskQuery = new TaskQuery(queryParameters);
    if(taskQuery.isValid()) {
      Page<Task> taskPage = taskService.find(taskQuery.toDomainQuery());
      return Response.status(OK).entity(taskMapper().domainPageToContentPage(taskPage)).build();
    } else {
      LOGGER.error("Invalid parameters on call to /task/list. Detailed error : {}",
              taskQuery.errorMessage());
      return Response.status(BAD_REQUEST).build();
    }
  }

  @GET
  @Path("/{taskId}")
  public Response findTaskById(
      @PathParam("taskId")
      @Pattern(regexp = UUID_PATTERN, message = "Task Id must be a valid UUID.")
          UUID taskId
  ) {
    return taskService.findById(taskId).map(task ->
            Response.status(OK).entity(taskMapper().domainToContent(task)).build())
            .orElse(Response.status(NOT_FOUND).build());
  }

  @POST
  @Path("/")
  public Response create(TaskContent taskContent) {
    TaskContent createdTask
        = taskMapper().domainToContent(
        taskService.insert(taskMapper().contentToDomain(taskContent)));
    return Response.status(OK).entity(createdTask).build();
  }

  private TaskMapperDecorator taskMapper() {
    return new TaskMapperDecorator();
  }
}
