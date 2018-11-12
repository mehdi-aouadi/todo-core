package com.todo.controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.todo.contents.ProgramTemplateContent;
import com.todo.mappers.ProgramTemplateMapper;
import com.todo.services.ProgramTemplateService;

import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("program/template")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgramTemplateController {

  ProgramTemplateService programTemplateService;
  ProgramTemplateMapper programTemplateMapper = ProgramTemplateMapper.INSTANCE;
  private Gson jsonSerializer = new Gson();

  @Inject
  public void ProgramTemplateController(ProgramTemplateService programTemplateService) {
    this.programTemplateService = programTemplateService;
  }

  @GET
  @Path("all")
  public Response getProgramTemplateList(
      @QueryParam("skip") int skip,
      @QueryParam("limit") @Max(100) int limit) {
    return Response.status(OK)
        .entity(jsonSerializer.toJson(
            programTemplateMapper.programTemplateListToContents(
                programTemplateService.getProgramTemplateByRange(skip, limit)
            )
        )).build();
  }

  @POST
  @Path("new")
  public Response createProgramTemplate(ProgramTemplateContent programTemplateContent) {
    return Response.status(CREATED).entity(jsonSerializer.toJson(
        programTemplateMapper.programTemplateToContent(
            programTemplateService.saveProgramTemplate(
                programTemplateMapper.contentToProgramTemplate(programTemplateContent)
            )
        )
    )).build();
  }
}
