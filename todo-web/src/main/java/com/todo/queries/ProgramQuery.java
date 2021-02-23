package com.todo.queries;

import javax.ws.rs.core.MultivaluedMap;

public class ProgramQuery extends Query<com.todo.repositories.queries.ProgramQuery> {

  public static final String NAME = "name";
  public static final String ORDER_NAME = "orderName";
  public static final String ORDER_CREATION_DATE = "orderCreationDate";
  public static final String ORDER_LAST_MODIFICATION_DATE = "orderLastModificationDate";

  /**
   * Build a {@link Query} from HTTP query parameters.
   *
   * @param queryParameters HTTP query parameters
   */
  public ProgramQuery(MultivaluedMap<String, String> queryParameters) {
    super(queryParameters);
  }

  @Override
  protected com.todo.repositories.queries.ProgramQuery buildQuery() {
    Page page = new Page(param(RANGE).orElse(null));
    return com.todo.repositories.queries.ProgramQuery.builder()
        .pageIndex(page.getPageIndex())
        .pageSize(page.getPageSize())
        .name(param(NAME).orElse(null))
        .nameOrder(paramOrder(ORDER_NAME))
        .creationDateOrder(paramOrder(ORDER_CREATION_DATE))
        .lastModificationDateOrder(paramOrder(ORDER_LAST_MODIFICATION_DATE))
        .build();
  }
}
