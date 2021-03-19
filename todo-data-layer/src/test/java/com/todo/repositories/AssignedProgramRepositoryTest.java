package com.todo.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.todo.dbutils.MongoDbManager;
import com.todo.model.AssignedProgram;
import com.todo.repositories.impl.AssignedProgramRepositoryMongoImpl;
import com.todo.repositories.queries.AssignedProgramQuery;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.hamcrest.junit.MatcherAssert.assertThat;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MongoDbManager.class)
public class AssignedProgramRepositoryTest {

  private MongoCollection<AssignedProgram> assignedProgramMongoCollectionMock;

  @Before
  public void before() {
    PowerMockito.mockStatic(MongoDbManager.class);
    assignedProgramMongoCollectionMock = mock(MongoCollection.class);
    BDDMockito.given(MongoDbManager.getMongoCollection(AssignedProgram.class)).willReturn(assignedProgramMongoCollectionMock);
    FindIterable<AssignedProgram> assignedProgramFilterable = mock(FindIterable.class);
    when(assignedProgramFilterable.skip(anyInt())).thenReturn(assignedProgramFilterable);
    when(assignedProgramFilterable.limit(anyInt())).thenReturn(assignedProgramFilterable);
    when(assignedProgramFilterable.into(any(List.class))).thenReturn(mock(List.class));
    when(assignedProgramMongoCollectionMock.find(any(Bson.class))).thenReturn(assignedProgramFilterable);
    when(assignedProgramMongoCollectionMock.find(any(Bson.class))).thenReturn(assignedProgramFilterable);
  }

  @Test
  public void findAssignedProgramByQueryTest() {
    UUID userId = UUID.randomUUID();
    AssignedProgramQuery assignedProgramQuery
            = AssignedProgramQuery.builder()
            .name("assigned program task name")
            .userId(userId)
            .pageIndex(2)
            .pageSize(10)
            .build();

    AssignedProgramRepository assignedProgramRepository = new AssignedProgramRepositoryMongoImpl();

    assignedProgramRepository.find(assignedProgramQuery);

    verify(assignedProgramMongoCollectionMock, times(1)).find((Bson) argThat(argument -> {
      assertThat(argument.toString(), is(assignedProgramQuery.toBsonFilter().toString()));
      return true;
    }));
  }
}
