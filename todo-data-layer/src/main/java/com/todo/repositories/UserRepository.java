package com.todo.repositories;

import com.todo.model.AssignedProgram;
import com.todo.model.User;

import java.util.UUID;

public interface UserRepository {

  User findUserByEmail(String userEmail);

  User saveUser(User user);

  boolean userExists(String userEmail);

  void deleteUserByEmail(String userEmail);

  void deleteUserById(UUID userId);

  AssignedProgram addAssignedProgram(AssignedProgram assignedProgram);

  AssignedProgram findAssignedProgramById(UUID assignedProgramId);

  void deleteAssignedProgram(UUID assignedProgramId);

}
