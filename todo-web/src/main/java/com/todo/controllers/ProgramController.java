package com.todo.controllers;

import javax.inject.Inject;
import com.todo.contents.ProgramContent;
import com.todo.mappers.ProgramMapper;
import com.todo.mappers.ProgramMapperDecorator;
import com.todo.queries.ProgramQuery;
import com.todo.services.ProgramService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("program")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
@Slf4j
public class ProgramController extends AbstractController {

  private ProgramService programService;
  private ProgramMapperDecorator programMapper = new ProgramMapperDecorator(ProgramMapper.INSTANCE);

  @Inject
  public ProgramController(ProgramService programService) {
    this.programService = programService;
  }

  @GET
  @Path("/list")
  public Response listPrograms(@Context UriInfo uriInfo) {

    MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

    ProgramQuery programQuery = new ProgramQuery(queryParameters);

    if(!programQuery.isValid()) {
      log.warn(
          "Invalid parameters on call to /program/list/ : {}. Detailed error : {}",
          queryParameters,
          programQuery.errorMessage());
      return Response
          .status(Response.Status.BAD_REQUEST)
          .build();
    } else {
      return Response.status(OK)
          .entity(
              programMapper.pageToProgramPageContent(
                  programService.findProgramsByQuery(
                      (com.todo.repositories.impl.queries.ProgramQuery) programQuery.toDomainQuery()
                  )
              )
          ).build();
    }
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
