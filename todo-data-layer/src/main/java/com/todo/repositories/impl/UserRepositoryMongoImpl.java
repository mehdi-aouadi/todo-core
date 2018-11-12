package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.todo.dbutils.DbManager;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class UserRepositoryMongoImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryMongoImpl.class);

  private final MongoCollection<User> mongoCollection = DbManager.getMongoCollection(User.class);

  @Override
  public User getUserByEmail(String email) {
    return mongoCollection.find(eq("email", email)).first();
  }

  @Override
  public User saveUser(User user) {
    if (user.getId() == null) {
      user.setId(UUID.randomUUID());
    }
    mongoCollection.insertOne(user);
    return new User(user.getId(), user.getEmail(), user.getPhoneNumber());
  }

  @Override
  public boolean userExists(String email) {
    return mongoCollection.find(eq("email", email)).first() != null;
  }
}
