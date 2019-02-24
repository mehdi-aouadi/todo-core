package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.services.TaskService;

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
import static javax.ws.rs.core.Response.Status.OK;

import lombok.NoArgsConstructor;

@Path("tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class TaskController {

  private TaskService taskService;
  private TaskMapper taskMapper = TaskMapper.INSTANCE;
  private Gson jsonSerializer = new Gson();

  @Inject
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GET
  @Path("/")
  public Response getAllUserAssignedTasksByEmail(@QueryParam("name") String name,
                                                 @QueryParam("skip") int skip,
                                                 @QueryParam("limit") @Max(100) int limit) {
    return Response.status(OK)
        .entity(jsonSerializer.toJson(taskMapper.mapFromDomain(
            taskService.findTasksByName(name, skip, limit))))
        .build();
  }

  @POST
  public Response create(TaskContent taskContent) {
    taskService.saveTask(taskMapper.mapToDomain(taskContent));
    return Response.status(OK).entity("TASK INSERTED SUCCESSFULLY").build();
  }
}
