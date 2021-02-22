package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.todo.exceptions.PersistenceException;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  /**
   * Finds a user by its email.
   * @param userEmail The {@link User} email
   * @return The {@link User} if found, null otherwise
   */
  Optional<User> findByEmail(String userEmail);

  /**
   * Finds a user by its id.
   * @param userId The {@link User} id
   * @return The {@link User} if found, null otherwise
   */
  Optional<User> findById(UUID userId);

  /**
   * Inserts a user.
   * @param user The {@link User} to insert
   * @return The inserted {@link User} or null if the insert operation fails
   */
  User insert(User user);

  /**
   * Updates an existing user.
   * @param user The {@link User} to update
   * @return The updated {@link User}
   * @throws ResourceNotFoundException if the {@link User} is not found
   */
  User update(User user) throws ResourceNotFoundException;

  /**
   * Checks if a user exists by its email.
   * @param userEmail The user email
   * @return true if a user with the given email exists, false otherwise
   */
  boolean userExists(String userEmail);

  /**
   * Checks if a user exists by its Id.
   * @param userId The user Id
   * @return true if a user with the given Id exists, false otherwise
   */
  boolean userExists(UUID userId);

  /**
   * Deletes a user by its email.
   * @param userEmail The user email
   * @return The delete operation result in a {@link DeleteResult}
   */
  DeleteResult deleteByEmail(String userEmail);

  /**
   * Deletes a user by its id
   * @param userId The user Id
   * @return The delete operation result in a {@link DeleteResult}
   */
  DeleteResult deleteById(UUID userId);

  /**
   * Assigns a program to a user by its id.
   * @param userId The user Id
   * @param programId The program Id
   * @return The update operation result in an {@link UpdateResult}
   */
  UpdateResult enrollProgram(UUID userId, UUID programId) throws PersistenceException;

  /**
   * Assigns a {@link com.todo.model.Program} to a {@link User} by its email.
   * @param userEmail The {@link User} email
   * @param programId The {@link com.todo.model.Program} Id
   * @return The update operation result in an {@link UpdateResult}
   */
  UpdateResult enrollProgram(String userEmail, UUID programId) throws PersistenceException;

  /**
   * Withdraw a {@link User} from a {@link com.todo.model.Program} by userId
   * @param userEmail the {@link User} email
   * @param programId the {@link com.todo.model.Program} Id
   * @return
   */
  UpdateResult withdrawProgram(String userEmail, UUID programId);

  /**
   * Withdraw a {@link User} from a {@link com.todo.model.Program}
   * @param userId the {@link User} Id
   * @param programId the {@link com.todo.model.Program} Id
   * @return
   */
  UpdateResult withdrawProgram(UUID userId, UUID programId);

}
