package com.m9i.controllers;

import com.m9i.model.Task;
import com.m9i.model.User;
import com.m9i.service.TaskService;
import com.m9i.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Controller
@Path("todo/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GET
    @Path("tasks/assigned")
    public Response getAllUserAssignedTasksByEmail(@QueryParam("email") String email) {
        return Response.status(200).entity(taskService.getAllUserAssignedTasksByEmail(email)).build();
    }

    @GET
    @Path("tasks/created")
    public Response getAllUserTasksByEmail(@QueryParam("email") String email) {
        return Response.status(200).entity(taskService.getAllUserCreatedTasksByEmail(email)).build();
    }

    @POST
    @Path("tasks")
    public Response create(Task task) {
        taskService.saveTask(task);
        return Response.status(Response.Status.OK).entity("TASK INSERTED SUCCESSFULLY").build();
    }

}
