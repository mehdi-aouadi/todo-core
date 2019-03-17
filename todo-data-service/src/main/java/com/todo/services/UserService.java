package com.todo.services;

import com.todo.model.AssignedProgram;
import com.todo.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

  User findUserByEmail(String email);

  User createUser(User user);

  User updateUser(User user);

  boolean userExists(String email);

  List<AssignedProgram> findAssignedProgramsByUserId(UUID userId, int skip, int limit);

  AssignedProgram findAssignedProgramById(UUID userId, UUID assignedProgramId);

  void addAssignedProgram(UUID programId, UUID userId);

}
