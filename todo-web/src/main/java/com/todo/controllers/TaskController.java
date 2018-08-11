package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("todo/tasks/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskController {

    private TaskService taskService;
    private TaskMapper taskMapper = TaskMapper.INSTANCE;
    private Gson jsonSerializer = new Gson();

    @Autowired
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
