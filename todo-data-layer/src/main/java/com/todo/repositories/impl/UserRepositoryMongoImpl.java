package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.todo.dbutils.DbManager;
import com.todo.model.AssignedProgram;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class UserRepositoryMongoImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryMongoImpl.class);

  private static final ReplaceOptions REPLACE_OPTIONS
      = ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true));

  private final MongoCollection<User> userMongoCollection
      = DbManager.getMongoCollection(User.class);

  @Override
  public User findUserByEmail(String userEmail) {
    LOGGER.info("Retrieving User. Email : {}", userEmail);
    return userMongoCollection.find(eq("email", userEmail)).first();
  }

  @Override
  public User saveUser(User user) {
    LOGGER.info("Inserting User : {}", user.toString());
    userMongoCollection.replaceOne(eq("id", user.getId().toString()), user, REPLACE_OPTIONS);
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
    userMongoCollection.deleteOne(eq("id", userId.toString()));
  }

  @Override
  public List<AssignedProgram> findAssignedProgramsByUserId(UUID userId, int skip, int limit) {
    LOGGER.info("Retrieving Assigned Programs of user id : {}", userId.toString());
    return userMongoCollection.find(eq("id", userId.toString())).first()
        .getAssignedPrograms().stream().skip(skip).limit(limit).collect(Collectors.toList());
  }

  @Override
  public UpdateResult addAssignedProgram(AssignedProgram assignedProgram, UUID userId) {
    LOGGER.info("Adding Assigned Program {} to User {}",
        assignedProgram.getId().toString(), userId.toString());
    return userMongoCollection.updateOne(
        eq("id", userId.toString()),
        Updates.addToSet("assignedPrograms", assignedProgram));
  }

  @Override
  public Optional<AssignedProgram> findAssignedProgramById(UUID assignedProgramId, UUID userId) {
    return userMongoCollection.find(eq("id",
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
        Updates.pull("assignedPrograms", eq("id", assignedProgramId.toString())));
  }
}
