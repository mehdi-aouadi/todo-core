package com.todo.repositories.queries;

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
public class MediaQuery extends Query {

  private static final String MEDIA_NAME_FIELD = "name";

  private String name;
  private Order nameOrder;
  private Order lastModificationDateOrder;
  private Order creationDateOrder;

  @Builder
  protected MediaQuery(
      Integer pageIndex,
      Integer pageSize,
      String name,
      Order nameOrder,
      Order lastModificationDateOrder,
      Order creationDateOrder) {
    super(pageIndex, pageSize);
    this.name = name;
    this.nameOrder
        = nameOrder != null ? nameOrder : Order.ASC;
    this.lastModificationDateOrder
        = lastModificationDateOrder != null ? lastModificationDateOrder : Order.ASC;
    this.creationDateOrder
        = creationDateOrder != null ? creationDateOrder : Order.ASC;
  }

  @Override
  public Bson toBsonFilter() {
    Bson filter = new BasicDBObject();
    if (this.name != null) {
      filter = Filters.eq(MEDIA_NAME_FIELD, this.name);
    }
    return filter;
  }

  public Bson nameOrderToBson() {
    if (this.nameOrder != null && this.nameOrder.equals(Order.DESC)) {
      return Sorts.descending(MEDIA_NAME_FIELD);
    } else {
      return Sorts.ascending(MEDIA_NAME_FIELD);
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
