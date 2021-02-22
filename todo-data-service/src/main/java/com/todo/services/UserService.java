package com.todo.services;

import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

  Optional<User> findByEmail(String email);

  Optional<User> findById(UUID userId);

  User insert(User user);

  User update(User user) throws ResourceNotFoundException;

  boolean userExists(String email);

  void deleteByEmail(String userEmail);

  void deleteById(UUID userId);

  void enrollProgram(UUID userId, UUID programId) throws DataIntegrityException;

  void enrollProgram(String userEmail, UUID programId);

  void withdrawProgram(String userEmail, UUID programId);

  void withdrawProgram(UUID userId, UUID programId);

}
