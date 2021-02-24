package com.todo.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.todo.dbutils.MongoDbManager;
import com.todo.model.*;
import com.todo.repositories.impl.AssignedProgramRepositoryMongoImpl;
import com.todo.repositories.impl.UserRepositoryMongoImpl;
import com.todo.repositories.queries.AssignedProgramQuery;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest(MongoDbManager.class)
public class UserRepositoryTest {

  private final UUID userId = UUID.randomUUID();
  private MongoCollection<User> userMongoCollectionMock;
  private MongoCollection<Program> programMongoCollectionMock;

  @Before
  public void before() {
    PowerMockito.mockStatic(MongoDbManager.class);
    userMongoCollectionMock = mock(MongoCollection.class);
    programMongoCollectionMock = mock(MongoCollection.class);
    BDDMockito.given(MongoDbManager.getMongoCollection(User.class)).willReturn(userMongoCollectionMock);
    BDDMockito.given(MongoDbManager.getMongoCollection(Program.class)).willReturn(programMongoCollectionMock);
    FindIterable<User> userFindIterable = mock(FindIterable.class);
    when(userMongoCollectionMock.find(any(Bson.class))).thenReturn(userFindIterable);
    List<AssignedProgram> userAssignedProgramList = Arrays.asList(
        AssignedProgram.builder()
            .id(UUID.randomUUID())
            .programId(UUID.randomUUID())
            .build(),
        AssignedProgram.builder()
            .id(UUID.randomUUID())
            .programId(UUID.randomUUID())
            .build()
    );
    User user = User.builder()
        .id(userId)
        .userProfile(UserProfile.builder()
            .userName("John Galt")
            .build())
        .assignedPrograms(userAssignedProgramList.stream().map(Entity::getId).collect(Collectors.toList())).build();
    when(userFindIterable.first()).thenReturn(user);
  }

  @Test
  public void findAssignedProgramByQueryTest() {
    AssignedProgramQuery assignedProgramQuery
        = AssignedProgramQuery.builder()
        .name("assigned program task name")
        .pageIndex(2)
        .pageSize(10)
        .build();

    UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
    AssignedProgramRepository assignedProgramRepository = new AssignedProgramRepositoryMongoImpl();

    assignedProgramRepository.find(assignedProgramQuery);

    verify(userMongoCollectionMock, times(1)).find(assignedProgramQuery.toBsonFilter());

  }

}
