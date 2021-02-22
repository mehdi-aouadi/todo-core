package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperation;
import com.todo.exceptions.DataOperationException;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.User;
import com.todo.model.UserHistory;
import com.todo.model.UserProfile;
import com.todo.repositories.AssignedProgramRepository;
import com.todo.repositories.UserRepository;
import com.todo.services.UserService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final String USER_ENTITY_NAME = User.class.getSimpleName();
  private static final String USER_ID_FIELD_NAME = "userId";
  private static final String USER_EMAIL_FIELD_NAME = "userEmail";
  private static final String USER_PROFILE_ID_FIELD_NAME = "userProfileId";
  private static final String USER_HISTORY_ID_FIELD_NAME = "userHistoryId";

  private UserRepository userRepository;

  private AssignedProgramRepository assignedProgramRepository;

  @Inject
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findById(UUID userId) {
    return userRepository.findById(userId);
  }

  @Override
  public User insert(User user) {
    checkUserForCreation(user);
    user.setId(UUID.randomUUID());
    user.getUserProfile().setId(UUID.randomUUID());
    user.getUserHistory().setId(UUID.randomUUID());
    user.setCreationDate(Instant.now());
    user.setLastModificationDate(Instant.now());
    User insertedUser = userRepository.insert(user);
    if (insertedUser == null) {
      throw DataOperationException.builder()
              .entityName(USER_ENTITY_NAME)
              .dataOperation(DataOperation.DELETE)
              .message("Unable to create the new User " + user.toString())
              .build();
    } else {
      return user;
    }
  }

  @Override
  public User update(User user) throws ResourceNotFoundException {
    checkUserForUpdate(user);
    user.setLastModificationDate(Instant.now());
    return userRepository.update(user);
  }

  @Override
  public boolean userExists(String email) {
    return userRepository.userExists(email);
  }

  @Override
  public void deleteByEmail(String userEmail) {
    DeleteResult userDeleteResult = userRepository.deleteByEmail(userEmail);
    if(!userDeleteResult.wasAcknowledged() || userDeleteResult.getDeletedCount() == 0) {
      LOGGER.error("Unable to delete user with email {} due to internal server error", userEmail);
      throw DataOperationException.builder()
              .entityName(USER_ENTITY_NAME)
              .message("Unable to delete user with email " + userEmail)
              .dataOperation(DataOperation.DELETE)
              .build();
    }
  }

  @Override
  public void deleteById(UUID userId) {
    DeleteResult userDeleteResult = userRepository.deleteById(userId);
    if(!userDeleteResult.wasAcknowledged() || userDeleteResult.getDeletedCount() == 0) {
      LOGGER.error("Unable to delete user with Id {} due to internal server error.", userId.toString());
      throw DataOperationException.builder()
              .entityName(USER_ENTITY_NAME)
              .message("Unable to delete user with Id " + userId.toString())
              .dataOperation(DataOperation.DELETE)
              .build();
    }
  }

  @Override
  public void enrollProgram(UUID userId, UUID programId) throws DataIntegrityException{
    if(userId == null || programId == null) {

    }
    LOGGER.info("Enrolling user with Id {} to Program with Id {}", userId.toString(), programId.toString());
    userRepository.enrollProgram(userId, programId);
  }

  @Override
  public void enrollProgram(String userEmail, UUID programId) {
    userRepository.enrollProgram(userEmail, programId);
  }

  @Override
  public void withdrawProgram(String userEmail, UUID programId) {

  }

  @Override
  public void withdrawProgram(UUID userId, UUID programId) {

  }

  private void checkUserForCreation(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User is null.");
    }
    if(user.getId() == null) {
      throw DataIntegrityException
              .builder()
              .fieldName(USER_ID_FIELD_NAME)
              .entityName(USER_ENTITY_NAME)
              .message("To update a User userId is mandatory")
              .build();
    }
    if(user.getUserProfile().getId() != null) {
      throw DataIntegrityException
              .builder()
              .entityName(UserProfile.class.getSimpleName())
              .fieldName(USER_PROFILE_ID_FIELD_NAME)
              .message("To create a new User userProfileId must be null")
              .build();
    }
    if(user.getUserHistory().getId() != null) {
      throw DataIntegrityException.builder()
              .fieldName(USER_HISTORY_ID_FIELD_NAME)
              .entityName(UserHistory.class.getSimpleName())
              .message("To create a new User userHistoryId must be null")
              .build();
    }
    if (StringUtils.isBlank(user.getUserProfile().getEmail())) {
      throw DataIntegrityException.builder()
              .entityName(USER_ENTITY_NAME)
              .fieldName(USER_EMAIL_FIELD_NAME)
              .message("Missing user email.")
              .build();
    }
  }

  private void checkUserForUpdate(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Cannot update User. User is null.");
    }
    if(user.getId() == null) {
      throw DataIntegrityException.builder()
              .entityName(USER_ENTITY_NAME)
              .fieldName(USER_ID_FIELD_NAME)
              .message("To update a user, Id is mandatory. User : " + user)
              .build();
    }
    if (StringUtils.isBlank(user.getUserProfile().getEmail())) {
      throw DataIntegrityException.builder()
              .entityName(USER_ENTITY_NAME)
              .fieldName(USER_EMAIL_FIELD_NAME)
              .message("Missing user email. USer : " + user)
              .build();
    }
  }
}
