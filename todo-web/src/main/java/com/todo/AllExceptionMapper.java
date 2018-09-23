package com.todo;

import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.PersistenceException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllExceptionMapper implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AllExceptionMapper.class);

  @Override
  public Response toResponse(Exception exception) {
    if (exception instanceof PersistenceException) {
      return Response.status(resolveStatus((PersistenceException) exception))
          .entity(buildEntity((PersistenceException) exception))
          .build();
    } else {
      LOGGER.error(exception.getMessage(), exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{ \"error\": \"Internal Error. Please contact service administrator at "
              + "api@todo.com\" }")
          .type(MediaType.APPLICATION_JSON)
          .build();
    }
  }

  private Response.Status resolveStatus(PersistenceException exception) {
    if (exception instanceof DataIntegrityException) {
      return Response.Status.BAD_REQUEST;
    } else {
      return Response.Status.BAD_REQUEST;
    }
  }

  private Object buildEntity(PersistenceException exception) {
    return new StringBuilder()
        .append("{ \"field\": \"")
        .append(exception.getFieldName())
        .append("\" ,")
        .append("\"message\": \"")
        .append(exception.getMessage())
        .append("\" }")
        .toString();
  }
}
