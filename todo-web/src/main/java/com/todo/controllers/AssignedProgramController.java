package com.todo.controllers;

import com.google.gson.Gson;
import com.todo.mappers.AssignedProgramMapper;
import com.todo.services.AssignedProgramService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("assigned/program/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AssignedProgramController {

  private AssignedProgramService assignedProgramService;
  private AssignedProgramMapper assignedProgramMapper;
  private Gson jsonSerializer = new Gson();

  @Inject
  public AssignedProgramController(AssignedProgramService assignedProgramService) {
    this.assignedProgramService = assignedProgramService;
  }

}
