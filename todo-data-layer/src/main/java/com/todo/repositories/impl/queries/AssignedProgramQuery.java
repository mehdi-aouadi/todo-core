package com.todo.repositories.impl.queries;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.todo.common.Query;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.conversions.Bson;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssignedProgramQuery extends Query {

  private static final String PROGRAM_TITLE_FIELD = "title";
  private UUID userId;
  private String title;

  @Builder
  protected AssignedProgramQuery(Integer pageIndex, Integer pageSize, UUID userId, String title) {
    super(pageIndex, pageSize);
    this.userId = userId;
    this.title = title;
  }

  @Override
  public Bson toBsonFilter() {
    Bson filter = new BasicDBObject();
    if(this.userId != null) {
      filter = Filters.eq(ID_FIELD, this.userId);
    }
    return filter;
  }

}
