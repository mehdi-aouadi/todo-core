package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.UpdateResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperationException;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.AssignedProgram;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.services.ServiceUtils;
import com.todo.services.UserService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
  public User createUser(User user) {
    if(user.getId() != null) {
      throw new DataIntegrityException("userId", "To create a new User userId must be null");
    } else {
      checkUser(user);
      return userRepository.insertUser(user);
    }
  }

  @Override
  public User updateUser(User user) {
    if(user.getId() == null) {
      throw new DataIntegrityException("userId", "To update a User userId is mandatory");
    } else {
      checkUser(user);
      return userRepository.updateUser(user);
    }
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
  public AssignedProgram findAssignedProgramById(UUID userId, UUID assignedProgramId) {
    Optional<AssignedProgram> assignedProgram
        = userRepository.findAssignedProgramById(assignedProgramId, userId);
    if (!assignedProgram.isPresent()) {
      throw new ResourceNotFoundException("userId, assignedProgramId",
          "Unable to find assigned program with id " + assignedProgramId.toString()
              + "for user with id " + userId.toString()
      );
    } else {
      return assignedProgram.get();
    }
  }

  @Override
  public void addAssignedProgram(UUID programId, UUID userId) {
    UpdateResult updateResult = userRepository.addAssignedProgram(programId, userId);
    if (!updateResult.wasAcknowledged() || updateResult.getModifiedCount() == 0) {
      throw new DataOperationException("Assigned Program",
          "Unable to assign Program " + programId.toString()
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
