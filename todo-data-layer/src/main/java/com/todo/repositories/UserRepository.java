package com.todo.repositories;

import com.mongodb.client.result.UpdateResult;
import com.todo.model.AssignedProgram;
import com.todo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  User findUserByEmail(String userEmail);

  User saveUser(User user);

  boolean userExists(String userEmail);

  void deleteUserByEmail(String userEmail);

  void deleteUserById(UUID userId);

  List<AssignedProgram> findAssignedProgramsByUserId(UUID userId, int skip, int limit);

  UpdateResult addAssignedProgram(AssignedProgram assignedProgram, UUID userId);

  Optional<AssignedProgram> findAssignedProgramById(UUID assignedProgramId, UUID userId);

  UpdateResult deleteAssignedProgram(UUID assignedProgramId, UUID userId);

}
