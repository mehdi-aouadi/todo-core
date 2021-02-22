package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.model.AssignedProgram;
import com.todo.repositories.impl.queries.AssignedProgramQuery;
import com.todo.repositories.utils.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AssignedProgramRepository extends Pageable<AssignedProgramQuery, AssignedProgram> {

    /**
     * Inserts a new {@link AssignedProgram}
     * @param assignedProgram The {@link AssignedProgram} to insert
     * @return The inserted {@link AssignedProgram} or null if the insert operation fails
     */
    AssignedProgram insert(AssignedProgram assignedProgram);

    /**
     * Finds an {@link AssignedProgram} by its Id
     * @param assignedProgramId The {@link AssignedProgram} Id
     * @return an {@link Optional<AssignedProgram>} with the {@link AssignedProgram} or with null if not found
     */
    Optional<AssignedProgram> findById(UUID assignedProgramId);

    /**
     * Deletes an {@link AssignedProgram} by Id
     * @param assignedProgramId The {@link AssignedProgram} Id to delete
     * @return a delete operation result. See {@link DeleteResult}
     */
    DeleteResult deleteById(UUID assignedProgramId);


}
