package com.todo.controllers;

import com.google.inject.Inject;
import com.todo.contents.ProgramContent;
import com.todo.mappers.ProgramMapper;
import com.todo.services.ProgramService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("program")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgramController extends AbstractController {

  private ProgramService programService;
  private ProgramMapper programMapper = ProgramMapper.INSTANCE;

  @Inject
  public ProgramController(ProgramService programService) {
    this.programService = programService;
  }

  @GET
  @Path("/list")
  public Response listPrograms(
      @QueryParam("skip") int skip,
      @QueryParam("limit") @Max(100) int limit
  ) {
    return Response.status(OK)
        .entity(
            programMapper.domainListToContentList(
                programService.findProgramsByRange(skip, limit)
            )
        ).build();
  }

  @GET
  @Path("/{programId}")
  public Response getProgramById(
      @PathParam("programId")
      @Pattern(regexp = UUID_PATTERN, message = "Program Id must be a valid UUID.")
          UUID programId) {
    return Response.status(OK)
        .entity(
            programMapper.domainToContent(
                programService.findProgramById(programId)
            )
        ).build();
  }

  @POST
  @Path("/")
  public Response createProgram(ProgramContent programContent) {
    return Response.status(CREATED).entity(
        programMapper.domainToContent(
            programService.createProgram(
                programMapper.contentToDomain(programContent)
            )
        )
    ).build();
  }
}
