package com.todo.repositories.impl.queries;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.todo.common.Order;
import com.todo.common.Query;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.conversions.Bson;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProgramQuery extends Query {

  private static final String PROGRAM_TITLE_FIELD = "title";

  private String title;
  private Order titleOrder;
  private Order lastModificationDateOrder;
  private Order creationDateOrder;

  @Builder
  protected ProgramQuery(
      Integer pageIndex,
      Integer pageSize,
      String title,
      Order titleOrder,
      Order lastModificationDateOrder,
      Order creationDateOrder) {
    super(pageIndex, pageSize);
    this.title = title;
    this.titleOrder
        = titleOrder != null ? titleOrder : Order.ASC;
    this.lastModificationDateOrder
        = lastModificationDateOrder != null ? lastModificationDateOrder : Order.ASC;
    this.creationDateOrder
        = creationDateOrder != null ? creationDateOrder : Order.ASC;
  }

  @Override
  public Bson toBsonFilter() {
    Bson filter = new BasicDBObject();
    if (this.title != null) {
      filter = Filters.eq(PROGRAM_TITLE_FIELD, this.title);
    }
    return filter;
  }

  public Bson titleOrderToBson() {
    if (this.titleOrder != null && this.titleOrder.equals(Order.DESC)) {
      return Sorts.descending(PROGRAM_TITLE_FIELD);
    } else {
      return Sorts.ascending(PROGRAM_TITLE_FIELD);
    }
  }

  public Bson lastModificationDateOrderToBson() {
    if (this.lastModificationDateOrder != null
        && this.lastModificationDateOrder.equals(Order.DESC)) {
      return Sorts.descending(LAST_MODIFICATION_DATE_FIELD);
    } else {
      return Sorts.ascending(LAST_MODIFICATION_DATE_FIELD);
    }
  }

  public Bson creationDateOrderToBson() {
    if(this.creationDateOrder != null
        && this.creationDateOrder.equals(Order.DESC)) {
      return Sorts.descending(CREATION_DATE_FIELD);
    } else {
      return Sorts.ascending(CREATION_DATE_FIELD);
    }
  }
}
