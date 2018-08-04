package com.todo.controllers;

import com.todo.contents.TaskContent;
import com.todo.mappers.TaskMapper;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("todo/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    private TaskMapper taskMapper;

    @GET
    @Path("tasks/assigned")
    public Response getAllUserAssignedTasksByEmail(@QueryParam("email") String email) {
        return Response.status(200)
                .entity(taskMapper.taskListToContentList(
                        taskService.getAllUserAssignedTasksByEmail(email)))
                .build();
    }

    @GET
    @Path("tasks/created")
    public Response getAllUserTasksByEmail(@QueryParam("email") String email) {
        return Response.status(200)
                .entity(taskMapper.taskListToContentList(
                        taskService.getAllUserCreatedTasksByEmail(email)))
                .build();
    }

    @POST
    @Path("tasks")
    public Response create(TaskContent taskContent) {
        taskService.saveTask(taskMapper.contentToTask(taskContent));
        return Response.status(Response.Status.OK).entity("TASK INSERTED SUCCESSFULLY").build();
    }

}
