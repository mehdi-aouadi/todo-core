package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.UpdateResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperationException;
import com.todo.model.AssignedProgram;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.services.ServiceUtils;
import com.todo.services.UserService;

import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class UserServiceImpl implements UserService, ServiceUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
  private UserRepository userRepository;

  @Inject
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  @Override
  public User saveUser(User user) {
    user.setId(checkId(user.getId()));
    checkUser(user);
    return userRepository.saveUser(user);
  }

  @Override
  public boolean userExists(String email) {
    return userRepository.userExists(email);
  }

  @Override
  public List<AssignedProgram> findAssignedProgramsByUserId(UUID userId, int skip, int limit) {
    limit = checkLimit(100, limit, LOGGER);
    return userRepository.findAssignedProgramsByUserId(userId, skip, limit);
  }

  @Override
  public void addAssignedProgram(AssignedProgram assignedProgram, UUID userId) {
    UpdateResult updateResult = userRepository.addAssignedProgram(assignedProgram, userId);
    if(!updateResult.wasAcknowledged() || updateResult.getModifiedCount() == 0) {
      throw new DataOperationException("Assigned Program",
          "Unable to add Assigned Program " + assignedProgram.getProgram().getTitle()
              + "with id " + assignedProgram.getId()
              + " to User with id " + userId.toString());
    }
  }

  private void checkUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User is null.");
    }
    if (StringUtils.isBlank(user.getUserProfile().getEmail())) {
      throw new DataIntegrityException("Email", "Missing user email.");
    }
  }
}
