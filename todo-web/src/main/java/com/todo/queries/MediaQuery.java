package com.todo.queries;

import javax.ws.rs.core.MultivaluedMap;

public class MediaQuery extends Query {

  public static final String NAME = "name";
  public static final String ORDER_NAME = "orderName";
  public static final String ORDER_CREATION_DATE = "orderCreationDate";
  public static final String ORDER_LAST_MODIFICATION_DATE = "orderLastModificationDate";

  public MediaQuery(MultivaluedMap<String, String> queryParameters) {
    super(queryParameters);
  }

  @Override
  protected com.todo.common.Query buildQuery() {
    Page page = new Page(param(RANGE).orElse(null));
    return com.todo.repositories.impl.queries.MediaQuery.builder()
        .pageIndex(page.getPageIndex())
        .pageSize(page.getPageSize())
        .name(param(NAME).orElse(null))
        .nameOrder(paramOrder(ORDER_NAME))
        .creationDateOrder(paramOrder(ORDER_CREATION_DATE))
        .lastModificationDateOrder(paramOrder(ORDER_LAST_MODIFICATION_DATE))
        .build();
  }
}
