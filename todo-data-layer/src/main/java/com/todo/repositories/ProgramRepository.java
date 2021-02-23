package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.Program;
import com.todo.repositories.queries.ProgramQuery;
import com.todo.repositories.utils.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProgramRepository extends Pageable<ProgramQuery, Program> {

  /**
   * Inserts a new Program.
   * @param program The {@link Program} to insert
   * @return The inserted {@link Program} or null if the insert operation fails
   */
  Program insert(Program program);

  /**
   * Updates an existing program.
   * @param program The {@link Program} to update
   * @return The updated {@link Program>}
   * @throws ResourceNotFoundException if the {@link Program} is not found
   */
  Program update(Program program) throws ResourceNotFoundException;

  /**
   * Finds a {@link Program} by its Id.
   * @param programId The {@link Program} id
   * @return An {@link Optional<Program>} with the {@link Program} or with null if not found
   */
  Optional<Program> findById(UUID programId);

  /**
   * Deletes a {@link Program} by its id.
   * @param programId The {@link Program} id
   * @return A delete operation result. See {@link DeleteResult}
   */
  DeleteResult deleteById(UUID programId);

}
