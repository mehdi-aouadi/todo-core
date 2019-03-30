package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.todo.dbutils.DbManager;
import com.todo.model.AssignedProgram;
import com.todo.model.Program;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.repositories.impl.queries.AssignedProgramQuery;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class UserRepositoryMongoImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryMongoImpl.class);

  private static final String ID_FIELD = "_id";

  private final MongoCollection<Program> programMongoCollection
      = DbManager.getMongoCollection(Program.class);

  private final MongoCollection<User> userMongoCollection
      = DbManager.getMongoCollection(User.class);

  @Override
  public User findUserByEmail(String userEmail) {
    LOGGER.info("Retrieving User. Email : {}", userEmail);
    return userMongoCollection.find(eq("email", userEmail)).first();
  }

  @Override
  public User insertUser(User user) {
    LOGGER.info("Inserting User : {}", user.toString());
    userMongoCollection.insertOne(user);
    return User.builder()
        .id(user.getId())
        .creationDate(user.getCreationDate())
        .lastModificationDate(user.getLastModificationDate())
        .userProfile(user.getUserProfile())
        .password(user.getPassword())
        .assignedPrograms(user.getAssignedPrograms())
        .userHistory(user.getUserHistory())
        .build();
  }

  @Override
  public User updateUser(User user) {
    LOGGER.info("Updatig User : {}", user.toString());
    userMongoCollection.findOneAndReplace(eq(ID_FIELD, user.getId()), user);
    return User.builder()
        .id(user.getId())
        .creationDate(user.getCreationDate())
        .lastModificationDate(user.getLastModificationDate())
        .userProfile(user.getUserProfile())
        .password(user.getPassword())
        .assignedPrograms(user.getAssignedPrograms())
        .userHistory(user.getUserHistory())
        .build();
  }

  @Override
  public boolean userExists(String userEmail) {
    LOGGER.info("Checking if User exists. Email : {}", userEmail);
    return userMongoCollection.find(eq("email", userEmail)).first() != null;
  }

  @Override
  public void deleteUserByEmail(String userEmail) {
    LOGGER.info("Deleting User. Email : {}", userEmail);
    userMongoCollection.deleteOne(eq("email", userEmail));
  }

  @Override
  public void deleteUserById(UUID userId) {
    LOGGER.info("Deleting User. Id : {}", userId.toString());
    userMongoCollection.deleteOne(eq(ID_FIELD, userId.toString()));
  }

  @Override
  public List<AssignedProgram> findAssignedProgramsByQuery(UUID userId, AssignedProgramQuery assignedProgramQuery) {
    LOGGER.info("Retrieving Assigned Programs by Query : {}", assignedProgramQuery);
    return userMongoCollection
        .find(eq(ID_FIELD, userId))
        .first()
        .getAssignedPrograms()
        .stream()
        .filter(assignedProgram ->
            assignedProgram.getProgram().getTitle().matches(assignedProgramQuery.getTitle()))
        .skip(assignedProgramQuery.getPageIndex())
        .limit(assignedProgramQuery.getPageSize())
        .collect(Collectors.toList());
  }

  @Override
  public Optional<AssignedProgram> findAssignedProgramsById(UUID userId, UUID assignedProgramId) {
    LOGGER.info("Retrieving Assigned Programs with id {} of user id : {}",
        assignedProgramId.toString(), userId.toString());
    User user = userMongoCollection.find(eq(ID_FIELD, userId.toString())).first();
    if (user == null) {
      return Optional.empty();
    } else {
      return userMongoCollection.find(eq(ID_FIELD, userId.toString())).first()
          .getAssignedPrograms().stream().filter(assignedProgram -> assignedProgram.getId().equals(assignedProgramId)).findFirst();
    }
  }

  @Override
  public UpdateResult addAssignedProgram(UUID programId, UUID userId) {
    LOGGER.info("Assigning Program {} to User {}",
        programId.toString(), userId.toString());
    Program programToAssign = programMongoCollection.find(eq(ID_FIELD, programId)).first();
    AssignedProgram assignedProgram = AssignedProgram.builder()
        .id(UUID.randomUUID())
        .creationDate(LocalDateTime.now())
        .lastModificationDate(LocalDateTime.now())
        .enrollmentDate(LocalDateTime.now())
        .startDate(null)
        .endDate(null)
        .program(programToAssign)
        .build();
    return userMongoCollection.updateOne(
        eq("id", userId.toString()),
        Updates.addToSet("assignedPrograms", assignedProgram));
  }

  @Override
  public Optional<AssignedProgram> findAssignedProgramById(UUID assignedProgramId, UUID userId) {
    return userMongoCollection.find(eq(ID_FIELD,
        userId.toString())).first().getAssignedPrograms()
        .stream()
        .filter(assignedProgram -> assignedProgram.getId().equals(assignedProgramId))
        .findFirst();
  }

  @Override
  public UpdateResult deleteAssignedProgram(UUID assignedProgramId, UUID userId) {
    LOGGER.info("Removing Assigned Program {} to User {}",
        assignedProgramId.toString(), userId.toString());
    return userMongoCollection.updateOne(
        eq("id", userId.toString()),
        Updates.pull("assignedPrograms", eq(ID_FIELD, assignedProgramId.toString())));
  }
}
