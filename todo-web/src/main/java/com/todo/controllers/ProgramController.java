package com.todo.controllers;

import com.google.inject.Inject;
import com.todo.contents.ProgramContent;
import com.todo.mappers.ProgramMapper;
import com.todo.queries.ProgramQuery;
import com.todo.services.ProgramService;

import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
  public Response listPrograms(@Context UriInfo uriInfo) {

    MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

    ProgramQuery programQuery = new ProgramQuery(queryParameters);

    return Response.status(OK)
        .entity(
            programMapper.domainListToContentList(
                programService.findProgramsByQuery(
                    (com.todo.repositories.impl.queries.ProgramQuery) programQuery.toDomainQuery()
                )
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
