package com.todo.queries;

import javax.ws.rs.core.MultivaluedMap;
import java.util.UUID;

public class AssignedProgramQuery extends Query {

  public static final String NAME = "name";
  public static final String ORDER_NAME = "orderName";
  public static final String ORDER_CREATION_DATE = "orderCreationDate";
  public static final String ORDER_LAST_MODIFICATION_DATE = "orderLastModificationDate";

  private UUID userId;

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
  protected com.todo.common.Query buildQuery() {
    Page page = new Page(param(RANGE).orElse(null));
    return com.todo.repositories.impl.queries.AssignedProgramQuery.builder()
        .userId(this.userId)
        .pageIndex(page.getPageIndex())
        .pageSize(page.getPageSize())
        .name(param(NAME).orElse(null))
        .build();
  }
}
