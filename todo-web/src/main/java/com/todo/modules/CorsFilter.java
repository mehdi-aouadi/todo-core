package com.todo.modules;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

  // Check https://stackoverflow.com/questions/28065963/how-to-handle-cors-using-jax-rs-with-jersey
  // for more details
  /**
   * Method for ContainerRequestFilter.
   */
  @Override
  public void filter(ContainerRequestContext request) throws IOException {

    // If it's a preflight request, we abort the request with
    // a 200 status, and the CORS headers are added in the
    // response filter method below.
    if (isPreflightRequest(request)) {
      request.abortWith(Response.ok().build());
      return;
    }
  }

  /**
   * A preflight request is an OPTIONS request
   * with an Origin header.
   */
  private static boolean isPreflightRequest(ContainerRequestContext request) {
    return request.getHeaderString("Origin") != null
        && request.getMethod().equalsIgnoreCase("OPTIONS");
  }

  /**
   * Method for ContainerResponseFilter.
   */
  @Override
  public void filter(ContainerRequestContext request, ContainerResponseContext response)
      throws IOException {

    // if there is no Origin header, then it is not a
    // cross origin request. We don't do anything.
    if (request.getHeaderString("Origin") == null) {
      return;
    }

    // If it is a preflight request, then we add all
    // the CORS headers here.
    if (isPreflightRequest(request)) {
      // response.getHeaders().add("Access-Control-Allow-Origin", "*");
      response.getHeaders().add("Access-Control-Allow-Headers",
          "origin, content-type, accept, authorization");
      response.getHeaders().add("Access-Control-Allow-Credentials", "true");
      response.getHeaders().add("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }

    // Cross origin requests can be either simple requests
    // or preflight request. We need to add this header
    // to both type of requests. Only preflight requests
    // need the previously added headers.
    response.getHeaders().add("Access-Control-Allow-Origin", "*");
  }
}
