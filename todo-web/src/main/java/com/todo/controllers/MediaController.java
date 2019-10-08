package com.todo.controllers;

import com.todo.contents.MediaContent;
import com.todo.mappers.MediaMapper;
import com.todo.queries.MediaQuery;
import com.todo.services.MediaService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.UUID;

import static javax.ws.rs.core.Response.Status.OK;

@Path("media")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
@Slf4j
public class MediaController extends AbstractController {

  private MediaService mediaService;
  private MediaMapper mediaMapper = MediaMapper.INSTANCE;

  @Inject
  public MediaController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @GET
  @Path("/list")
  public Response listMedia(@Context UriInfo uriInfo) {
    MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
    MediaQuery mediaQuery = new MediaQuery(queryParameters);
    return Response.status(OK)
        .entity(
            mediaMapper.domainListToContentList(
                mediaService.findMediasByQuery(
                    (com.todo.repositories.impl.queries.MediaQuery) mediaQuery.toDomainQuery()
                )
            )
        ).build();
  }

  @GET
  @Path("/{mediaId}")
  public Response getMediaById(
      @PathParam("mediaId")
      @Pattern(regexp = UUID_PATTERN, message = "Media Id must be a valid UUID.")
          UUID mediaId) {
    return Response.status(OK)
        .entity(
            mediaMapper.domainToContent(
                mediaService.findMediaById(mediaId).get()
            )
        ).build();
  }

  @POST
  @Path("/")
  public Response createMedia(MediaContent mediaContent) {
    return Response.status(OK)
        .entity(
            mediaMapper.domainToContent(
                mediaService.createMedia(
                    mediaMapper.contentToDomain(mediaContent)
                )
            )
        ).build();
  }

}
