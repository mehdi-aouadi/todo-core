package com.m9i.controllers;

import com.m9i.model.Task;
import com.m9i.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("todo/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GET
    @Path("task")
    public Response getTasks() {
        return Response.status(200).entity("Get OK !").build();
    }

    @POST
    @Path("task")
    public Response postTask(Task task) {
        taskService.saveTask(task);
        return Response.status(Response.Status.OK).entity("Post OK !").build();

    }

}
