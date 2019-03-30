package com.todo.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.conversions.Bson;

@Getter
@EqualsAndHashCode
@ToString
public abstract class Query {

  protected static final String LAST_MODIFICATION_DATE_FIELD = "lastModificationDate";
  protected static final String CREATION_DATE_FIELD = "creationDate";
  protected static final String ID_FIELD = "_id";

  private final Integer pageIndex;
  private final Integer pageSize;
  private static final int DEFAULT_PAGE_SIZE = 50;

  protected Query(Integer pageIndex, Integer pageSize) {
    this.pageIndex = pageIndex != null ? pageIndex : 0;
    this.pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
  }

  protected abstract Bson toBsonFilter();

}