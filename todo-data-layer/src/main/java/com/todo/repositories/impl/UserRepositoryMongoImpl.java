package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.todo.dbutils.DbManager;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class UserRepositoryMongoImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryMongoImpl.class);

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
    LOGGER.info("Deleting USer. Id : {}", userId.toString());
    userMongoCollection.deleteOne(eq("id", userId.toString()));
  }
}
