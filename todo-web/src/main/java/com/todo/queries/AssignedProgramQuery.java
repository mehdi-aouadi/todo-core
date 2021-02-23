package com.todo.queries;

import javax.ws.rs.core.MultivaluedMap;
import java.util.UUID;

public class AssignedProgramQuery extends Query<com.todo.repositories.queries.AssignedProgramQuery> {

  public static final String NAME = "name";
  private final UUID userId;

  /**
   * Build a {@link Query} from HTTP query parameters.
   *
   * @param queryParameters HTTP query parameters
   */
  public AssignedProgramQuery(MultivaluedMap<String, String> queryParameters, UUID userId) {
    super(queryParameters);
    this.userId = userId;
  }

  @Override
  protected com.todo.repositories.queries.AssignedProgramQuery buildQuery() {
    Page page = new Page(param(RANGE).orElse(null));
    return com.todo.repositories.queries.AssignedProgramQuery.builder()
        .userId(this.userId)
        .pageIndex(page.getPageIndex())
        .pageSize(page.getPageSize())
        .name(param(NAME).orElse(null))
        .build();
  }
}
