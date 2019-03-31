package com.todo.repositories;

import com.mongodb.client.result.UpdateResult;
import com.todo.model.AssignedProgram;
import com.todo.model.User;
import com.todo.repositories.impl.queries.AssignedProgramQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  User findUserByEmail(String userEmail);

  User insertUser(User user);

  User updateUser(User user);

  boolean userExists(String userEmail);

  void deleteUserByEmail(String userEmail);

  void deleteUserById(UUID userId);

  List<AssignedProgram> findAssignedProgramsByQuery(AssignedProgramQuery assignedProgramQuery);

  Optional<AssignedProgram> findAssignedProgramsById(UUID userId, UUID assignedProgramId);

  UpdateResult addAssignedProgram(UUID programId, UUID userId);

  Optional<AssignedProgram> findAssignedProgramById(UUID assignedProgramId, UUID userId);

  UpdateResult deleteAssignedProgram(UUID assignedProgramId, UUID userId);

}
