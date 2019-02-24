package com.todo.services;

import com.todo.model.AssignedProgram;
import com.todo.model.User;

import java.util.UUID;

public interface UserService {

  User findUserByEmail(String email);

  User saveUser(User user);

  boolean userExists(String email);

  AssignedProgram addAssignedProgram(AssignedProgram assignedProgram, UUID userId);

}
