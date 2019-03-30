package com.todo.repositories.impl.queries;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.todo.common.Query;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.conversions.Bson;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssignedProgramQuery extends Query {

  private static final String PROGRAM_TITLE_FIELD = "title";
  private String title;

  @Builder
  protected AssignedProgramQuery(Integer pageIndex, Integer pageSize, UUID userId, String title) {
    super(pageIndex, pageSize);
    this.title = title;
  }

  @Override
  public Bson toBsonFilter() {
    throw new NotImplementedException();
  }
}
