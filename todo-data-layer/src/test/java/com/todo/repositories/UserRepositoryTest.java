package com.todo.repositories;

import com.mongodb.client.MongoCollection;
import com.todo.dbutils.DbManager;
import com.todo.model.User;
import com.todo.repositories.impl.UserRepositoryMongoImpl;
import com.todo.repositories.impl.queries.AssignedProgramQuery;
import org.bson.conversions.Bson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

  @Mock
  MongoCollection userMongoCollectionMock;
  @Mock
  DbManager dbManagerMcok;
  private UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
  private final UUID userId = UUID.randomUUID();

  @Before
  public void before() {
    when(dbManagerMcok.getMongoCollection(User.class)).thenReturn(userMongoCollectionMock);
  }

  @Test
  public void findAssignedProgramByQueryTest() {
    AssignedProgramQuery assignedProgramQuery
        = AssignedProgramQuery.builder()
        .title("assigned program task name")
        .pageIndex(2)
        .pageSize(10)
        .build();

    when(userMongoCollectionMock.find(Bson.class).first()).thenReturn(null);
    userRepositoryMongo.findAssignedProgramsByQuery(assignedProgramQuery);

    verify(userMongoCollectionMock).find();

  }

}
