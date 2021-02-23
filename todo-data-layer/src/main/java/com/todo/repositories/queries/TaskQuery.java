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

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskQuery extends Query {

  private static final String TASK_NAME_FIELD = "name";
  private static final String TASK_PROGRAM_ID_FIELD = "programId";
  private static final String TASK_MODULE_ID_FIELD = "moduleId";

  private String name;
  private UUID programId;
  private UUID moduleId;
  private Order nameOrder;
  private Order lastModificationDateOrder;
  private Order creationDateOrder;

  @Builder
  protected TaskQuery(
      Integer pageIndex,
      Integer pageSize,
      String name,
      UUID programId,
      UUID moduleId,
      Order nameOrder,
      Order lastModificationDateOrder,
      Order creationDateOrder) {
    super(pageIndex, pageSize);
    this.name = name;
    this.programId = programId;
    this.moduleId = moduleId;
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
      filter = Filters.regex(TASK_NAME_FIELD, this.name, "i");
    }
    if(this.moduleId != null) {
      filter = Filters.and(filter, Filters.eq(this.TASK_MODULE_ID_FIELD, this.moduleId.toString()));
    }
    if(this.programId != null) {
      filter = Filters.and(filter, Filters.eq(this.TASK_PROGRAM_ID_FIELD, this.programId.toString()));
    }
    return filter;
  }

  public Bson nameOrderToBson() {
    if (this.nameOrder != null && this.nameOrder.equals(Order.DESC)) {
      return Sorts.descending(TASK_NAME_FIELD);
    } else {
      return Sorts.ascending(TASK_NAME_FIELD);
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
