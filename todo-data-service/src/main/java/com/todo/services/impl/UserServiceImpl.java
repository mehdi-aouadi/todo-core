package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.UpdateResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperationException;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.AssignedProgram;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.repositories.impl.queries.AssignedProgramQuery;
import com.todo.services.UserService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public class UserServiceImpl implements UserService {

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
    checkUserForCreation(user);
    user.setId(UUID.randomUUID());
    user.getUserProfile().setId(UUID.randomUUID());
    user.getUserHistory().setId(UUID.randomUUID());
    return userRepository.insertUser(user);
  }

  @Override
  public User updateUser(User user) {
    if(user.getId() == null) {
      throw new DataIntegrityException("userId", "To update a User userId is mandatory");
    } else {
      checkUserForUpdate(user);
      return userRepository.updateUser(user);
    }
  }

  @Override
  public boolean userExists(String email) {
    return userRepository.userExists(email);
  }

  @Override
  public List<AssignedProgram> findAssignedProgramsByQuery(UUID userId, AssignedProgramQuery assignedProgramQuery) {
    return userRepository.findAssignedProgramsByQuery(userId, assignedProgramQuery);
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

  private void checkUserForCreation(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User is null.");
    }
    if(user.getId() != null) {
      throw new DataIntegrityException("userId", "To create a new User userId must be null");
    }
    if(user.getUserProfile().getId() != null) {
      throw new DataIntegrityException("userProfileId",
          "To create a new User userProfileId must be null");
    }
    if(user.getUserHistory().getId() != null) {
      throw new DataIntegrityException("userHistoryId",
          "To create a new User userHistoryId must be null");
    }
    if (StringUtils.isBlank(user.getUserProfile().getEmail())) {
      throw new DataIntegrityException("Email", "Missing user email.");
    }
  }

  private void checkUserForUpdate(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User is null.");
    }
    if (StringUtils.isBlank(user.getUserProfile().getEmail())) {
      throw new DataIntegrityException("Email", "Missing user email.");
    }
  }
}
