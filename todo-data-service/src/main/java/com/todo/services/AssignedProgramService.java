package com.todo.services;

import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.model.AssignedProgram;
import com.todo.repositories.queries.AssignedProgramQuery;

import java.util.Optional;
import java.util.UUID;

public interface AssignedProgramService {

    AssignedProgram insert(AssignedProgram assignedProgram);

    /**
     * Finds an {@link AssignedProgram} by its Id
     * @param assignedProgramId The {@link AssignedProgram} Id
     * @return an {@link Optional <AssignedProgram>} with the {@link AssignedProgram} or with null if not found
     */
    Optional<AssignedProgram> findById(UUID assignedProgramId);

    /**
     * Finds a set of {@link AssignedProgram} by a search query
     * @param assignedProgramQuery the search query. See {@link AssignedProgramQuery}
     * @return a {@link Page<AssignedProgram>} with the mathcing {@link AssignedProgram}
     */
    Page<AssignedProgram> find(AssignedProgramQuery assignedProgramQuery);

    /**
     * Deletes an {@link AssignedProgram} by Id
     * @param assignedProgramId The {@link AssignedProgram} Id to delete
     * @return a delete operation result. See {@link DeleteResult}
     */
    void deleteById(UUID assignedProgramId);

}
