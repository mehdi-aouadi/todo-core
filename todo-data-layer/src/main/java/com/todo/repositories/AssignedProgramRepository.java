package com.todo.repositories;

import com.todo.model.AssignedProgram;

import java.util.UUID;

public interface AssignedProgramRepository {
  AssignedProgram saveAssignedProgram(AssignedProgram assignedProgram);
  AssignedProgram findAssignedProgramById(UUID assignedProgramId);
  void deleteAssignedProgram(UUID assignedProgramId);
}
