package com.todo.repositories.impl.queries;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.todo.common.Query;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.bson.conversions.Bson;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssignedProgramQuery extends Query {

  private static final String PROGRAM_NAME_FIELD = "name";
  private UUID userId;
  private String name;

  @Builder
  protected AssignedProgramQuery(Integer pageIndex, Integer pageSize, UUID userId, String name) {
    super(pageIndex, pageSize);
    this.userId = userId;
    this.name = name;
  }

  @Override
  public Bson toBsonFilter() {
    Bson filter = new BasicDBObject();
    if(this.userId != null) {
      filter = Filters.eq(ID_FIELD, this.userId);
    }
    if(!StringUtils.isBlank(this.name)) {
      filter = Filters.and(filter, Filters.regex(PROGRAM_NAME_FIELD, this.name, "i"));
    }
    return filter;
  }

}
