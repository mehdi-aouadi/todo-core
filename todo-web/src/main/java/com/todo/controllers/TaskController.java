package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.services.TaskService;

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
  @Path("assigned")
  public Response getAllUserAssignedTasksByEmail(@QueryParam("email") String email) {
    return Response.status(200)
        .entity(jsonSerializer.toJson(taskMapper.taskListToContentList(
            taskService.getAllUserAssignedTasksByEmail(email))))
        .build();
  }

  @GET
  @Path("created")
  public Response getAllUserTasksByEmail(@QueryParam("email") String email) {
    return Response.status(200)
        .entity(jsonSerializer.toJson(taskMapper.taskListToContentList(
            taskService.getAllUserCreatedTasksByEmail(email))))
        .build();
  }

  @POST
  public Response create(TaskContent taskContent) {
    taskService.saveTask(taskMapper.contentToTask(taskContent));
    return Response.status(Response.Status.OK).entity("TASK INSERTED SUCCESSFULLY").build();
  }
}
