package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperation;
import com.todo.exceptions.DataOperationException;
import com.todo.exceptions.PersistenceException;
import com.todo.model.AssignedProgram;
import com.todo.repositories.AssignedProgramRepository;
import com.todo.repositories.impl.queries.AssignedProgramQuery;
import com.todo.services.AssignedProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class AssignedProgramServiceImpl implements AssignedProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);
    private static final String ASSIGNED_PROGRAM_ENTITY_NAME = AssignedProgram.class.getSimpleName();
    private static final String ASSIGNED_PROGRAM_ID_FIELD_NAME = "assignedProgramId";
    private static final String ASSIGNED_PROGRAM_PROGRAM_ID_FIELD_NAME = "programId";

    private AssignedProgramRepository assignedProgramRepository;

    @Inject
    public AssignedProgramServiceImpl(AssignedProgramRepository assignedProgramRepository) {
        this.assignedProgramRepository = assignedProgramRepository;
    }

    @Override
    public AssignedProgram insert(AssignedProgram assignedProgram) {
        checkAssignedProgramForCreation(assignedProgram);
        assignedProgram.setId(UUID.randomUUID());
        assignedProgram.setCreationDate(Instant.now());
        assignedProgram.setLastModificationDate(Instant.now());
        LOGGER.info("Inserting Assigned Program {}", assignedProgram);
        AssignedProgram insertedAssignedProgram = assignedProgramRepository.insert(assignedProgram);
        if(insertedAssignedProgram == null) {
            LOGGER.error("Unable to insert Assigned Program due to internal server error. Assigned Program : "
            + assignedProgram.toString());
            throw DataOperationException.builder()
                    .dataOperation(DataOperation.INSERT)
                    .entityName(ASSIGNED_PROGRAM_ENTITY_NAME)
                    .message("Unable to insert Assigned Program due to internal server error")
                    .build();

        } else {
            return insertedAssignedProgram;
        }
    }

    @Override
    public Optional<AssignedProgram> findById(UUID assignedProgramId) {
        return assignedProgramRepository.findById(assignedProgramId);
    }

    @Override
    public void deleteById(UUID assignedProgramId) {
        DeleteResult deleteResult = assignedProgramRepository.deleteById(assignedProgramId);
        if(!deleteResult.wasAcknowledged() || deleteResult.getDeletedCount() == 0) {
            LOGGER.error("Unable to delete Assigned Program with Id {}", assignedProgramId);
            throw DataOperationException.builder()
                    .dataOperation(DataOperation.DELETE)
                    .entityName(ASSIGNED_PROGRAM_ENTITY_NAME)
                    .message("Unable to delete Assigned Program with Id " + assignedProgramId.toString())
                    .build();
        }
    }

    @Override
    public Page<AssignedProgram> find(AssignedProgramQuery assignedProgramQuery) {
        return assignedProgramRepository.find(assignedProgramQuery);
    }

    private void checkAssignedProgramForCreation(AssignedProgram assignedProgram) {
        if(assignedProgram == null) {
            LOGGER.error("Cannot insert a null Assigned Program");
            throw PersistenceException.builder()
                    .entityName(ASSIGNED_PROGRAM_ENTITY_NAME)
                    .message("Cannot insert a null Assigned Program")
                    .build();
        }
        if(assignedProgram.getId() != null) {
            LOGGER.error("To create a new Assigned Program the Id field must be null. Assigned Program {}",
                    assignedProgram.toString());
            throw DataIntegrityException.builder()
                    .entityName(ASSIGNED_PROGRAM_ENTITY_NAME)
                    .fieldName(ASSIGNED_PROGRAM_ID_FIELD_NAME)
                    .message("To create a new Assigned Program the Id field must be null")
                    .build();
        }
        if(assignedProgram.getProgramId() == null) {
            LOGGER.error("To create a new Assigned Program the programId field is mandatory. Assigned Program {}",
                    assignedProgram.toString());
            throw DataIntegrityException.builder()
                    .entityName(ASSIGNED_PROGRAM_ENTITY_NAME)
                    .fieldName(ASSIGNED_PROGRAM_PROGRAM_ID_FIELD_NAME)
                    .message("To create a new Assigned Program the programId field is mandatory")
                    .build();
        }
    }

}
