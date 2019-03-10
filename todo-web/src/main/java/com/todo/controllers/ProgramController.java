package com.todo.controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.todo.contents.ProgramContent;
import com.todo.mappers.ProgramMapper;
import com.todo.services.ProgramService;

import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("program/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgramController {

  private ProgramService programService;
  private ProgramMapper programMapper = ProgramMapper.INSTANCE;
  private Gson jsonSerializer = new Gson();

  @Inject
  public void ProgramTemplateController(ProgramService programService) {
    this.programService = programService;
  }

  @GET
  @Path("all")
  public Response getProgramTemplateList(
      @QueryParam("skip") int skip,
      @QueryParam("limit") @Max(100) int limit) {
    return Response.status(OK)
        .entity(jsonSerializer.toJson(
            programMapper.domainListToContentList(
                programService.findProgramsByRange(skip, limit)
            )
        )).build();
  }

  @POST
  @Path("new")
  public Response createProgram(ProgramContent programContent) {
    return Response.status(CREATED).entity(jsonSerializer.toJson(
        programMapper.domainToContent(
            programService.saveProgram(
                programMapper.contentToDomain(programContent)
            )
        )
    )).build();
  }
}
