package com.todo.repositories.impl;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.todo.dbutils.MongoDbManager;
import com.todo.exceptions.DataOperation;
import com.todo.exceptions.DataOperationException;
import com.todo.exceptions.PersistenceException;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.AssignedModule;
import com.todo.model.AssignedProgram;
import com.todo.model.Program;
import com.todo.model.User;
import com.todo.repositories.AssignedProgramRepository;
import com.todo.repositories.ProgramRepository;
import com.todo.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

@NoArgsConstructor
public class UserRepositoryMongoImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryMongoImpl.class);

  private static final String USER_ENTITY_NAME = User.class.getSimpleName();
  private static final String ASSIGNED_PROGRAM_ENTITY_NAME = AssignedProgram.class.getSimpleName();
  private static final String USER_ID_FIELD = "_id";
  private static final String USER_PROFILE_FIELD = "userProfile";
  private static final String USER_EMAIL_FIELD = "email";
  private static final String USER_ASSIGNED_PROGRAMS_FIELD = "assignedPrograms";

  private ProgramRepository programRepository;
  private AssignedProgramRepository assignedProgramRepository;

  private final MongoCollection<User> userMongoCollection
      = MongoDbManager.getMongoCollection(User.class);

  @Inject
  public UserRepositoryMongoImpl(ProgramRepository programRepository,
                                 AssignedProgramRepository assignedProgramRepository) {
    this.programRepository = programRepository;
    this.assignedProgramRepository = assignedProgramRepository;
  }

  @Override
  public Optional<User> findByEmail(String userEmail) {
    LOGGER.info("Retrieving User. Email : {}", userEmail);
    return Optional.ofNullable(userMongoCollection.find(eq(USER_PROFILE_FIELD + '.' + USER_EMAIL_FIELD, userEmail)).first());
  }

  @Override
  public Optional<User> findById(UUID userId) {
    LOGGER.info("Retrieving User. Id : {}", userId);
    return Optional.ofNullable(userMongoCollection.find(eq(USER_ID_FIELD, userId.toString())).first());
  }

  @Override
  public User insert(User user) {
    LOGGER.info("Inserting a new User : {}", user.toString());
    try {
      userMongoCollection.insertOne(user);
      return user;
    } catch (Exception e) {
      LOGGER.error("Insert operation of User {} failed due to internal error. Stack Trace : {}",
              user.getUserProfile().getEmail(),
              e.getStackTrace().toString());
      return null;
    }
  }

  @Override
  public User update(User user) throws ResourceNotFoundException {
    LOGGER.info("Updating User : {}", user.toString());
    return Optional.ofNullable(userMongoCollection.findOneAndReplace(eq(USER_ID_FIELD, user.getId()), user))
            .orElseThrow(() -> ResourceNotFoundException.builder()
              .entityName(User.class.getSimpleName())
              .message("User with id " + user.getId() + " to update not found")
              .build());
  }

  @Override
  public boolean userExists(String userEmail) {
    LOGGER.info("Checking if User with email {} exists", userEmail);
    return userMongoCollection.countDocuments(eq(USER_PROFILE_FIELD + "." + USER_EMAIL_FIELD, userEmail)) >= 0;
  }

  @Override
  public boolean userExists(UUID userId) {
    LOGGER.info("Checking if User with Id {} exists", userId.toString());
    return userMongoCollection.countDocuments(eq(USER_ID_FIELD, userId.toString())) >= 0;
  }

  @Override
  public DeleteResult deleteByEmail(String userEmail) {
    LOGGER.info("Deleting User with email {}", userEmail);
    return userMongoCollection.deleteOne(eq(USER_PROFILE_FIELD + "." + USER_EMAIL_FIELD, userEmail));
  }

  @Override
  public DeleteResult deleteById(UUID userId) {
    LOGGER.info("Deleting User with email {}", userId.toString());
    return userMongoCollection.deleteOne(eq(USER_ID_FIELD, userId.toString()));
  }

  @Override
  public UpdateResult enrollProgram(String userEmail, UUID programId) throws PersistenceException {
    LOGGER.info("Assigning Program with id {} to User with email {}",
            programId.toString(),
            userEmail);
    if(!this.userExists(userEmail)) {
      LOGGER.error("Unable to enroll User with email {} to Program with Id {}. The user doesn't exist",
              userEmail,
              programId.toString()
      );
      throw ResourceNotFoundException.builder().entityName(USER_ENTITY_NAME)
              .message("User with email " + userEmail + " does not exist")
              .build();
    }
    Optional<User> user = this.findByEmail(userEmail);
    return this.enrollUserToProgram(user.get().getId(), programId);
  }

  @Override
  public UpdateResult enrollProgram(UUID userId, UUID programId) throws ResourceNotFoundException {
    LOGGER.info("Assigning Program with id {} to User with Id {}",
            programId.toString(),
            userId.toString());
    if(!this.userExists(userId)) {
      LOGGER.error("Unable to enroll User with Id {} to Program with Id {}. The user doesn't exist",
              userId.toString(),
              programId.toString()
      );
      throw ResourceNotFoundException.builder().entityName(USER_ENTITY_NAME)
              .message("User with Id " + userId.toString() + " does not exist")
              .build();
    }

    return this.enrollUserToProgram(userId, programId);
  }

  private UpdateResult enrollUserToProgram(UUID userId, UUID programId) {
    Optional<Program> programToAssign = programRepository.findById(programId);
    if(!programToAssign.isPresent()) {
      throw ResourceNotFoundException
              .builder()
              .entityName(Program.class.getSimpleName())
              .message("Cannot find Program with id "
                      + programId.toString()
                      + " to assign to User with id "
                      + userId.toString())
              .build();
    }
    List<AssignedModule> assignedModules
            = programToAssign.get().getModules().stream()
            .map(programModule ->
                    AssignedModule.builder()
                            .id(UUID.randomUUID())
                            .creationDate(Instant.now())
                            .lastModificationDate(Instant.now())
                            .userId(userId)
                            .id(UUID.randomUUID())
                            .moduleId(programModule.getId())
                            .finished(false)
                            .startDate(null)
                            .endDate(null)
                            .build())
            .collect(Collectors.toList());

    AssignedProgram assignedProgram = AssignedProgram.builder()
            .id(UUID.randomUUID())
            .creationDate(Instant.now())
            .lastModificationDate(Instant.now())
            .enrollmentDate(Instant.now())
            .startDate(null)
            .endDate(null)
            .assignedModules(assignedModules)
            .build();

    AssignedProgram insertedAssignedProgram = assignedProgramRepository.insert(assignedProgram);

    if(insertedAssignedProgram == null) {
      LOGGER.error("Unable to insert Assigned Program with program Id {} (Program id {}, name {}) to user with Id {}",
              programToAssign.get().getId(),
              programToAssign.get().getName(),
              assignedProgram.getProgramId(),
              userId.toString());
      throw DataOperationException.builder()
              .entityName(AssignedProgram.class.getSimpleName())
              .message("Unable to insert Assigned Program with id "
                      + assignedProgram.getProgramId()
                      + " (Program Id "
                      + programToAssign.get().getId()
                      + ", name "
                      + programToAssign.get().getName()
                      + ") to user with Id "
                      + userId.toString()
              ).dataOperation(DataOperation.INSERT)
              .build();
    }

    return userMongoCollection.updateOne(
            eq(USER_ID_FIELD, userId.toString()),
            push(USER_ASSIGNED_PROGRAMS_FIELD, programId.toString())
    );
  }

  @Override
  public UpdateResult withdrawProgram(String userEmail, UUID programId) {
    LOGGER.info("Removing Assigned Program {} to User with email {}",
        programId.toString(), userEmail);
    return userMongoCollection.updateOne(
        eq(USER_PROFILE_FIELD + "." + USER_EMAIL_FIELD, userEmail),
        Updates.pull(USER_ASSIGNED_PROGRAMS_FIELD, eq(programId.toString())));
  }

  @Override
  public UpdateResult withdrawProgram(UUID userId, UUID programId) {
    LOGGER.info("Removing Assigned Program {} to User with Id {}",
            programId.toString(), userId.toString());
    return userMongoCollection.updateOne(
            eq(USER_ID_FIELD, userId.toString()),
            Updates.pull(USER_ASSIGNED_PROGRAMS_FIELD, eq(programId.toString())));
  }
}
