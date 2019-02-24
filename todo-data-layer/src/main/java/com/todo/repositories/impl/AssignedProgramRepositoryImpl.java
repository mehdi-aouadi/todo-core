package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.todo.dbutils.DbManager;
import com.todo.model.AssignedProgram;
import com.todo.repositories.AssignedProgramRepository;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

public class AssignedProgramRepositoryImpl implements AssignedProgramRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignedProgramRepositoryImpl.class);

  private final MongoCollection<AssignedProgram> assignedProgramMongoCollection
      = DbManager.getMongoCollection(AssignedProgram.class);

  @Override
  public AssignedProgram saveAssignedProgram(AssignedProgram assignedProgram) {
    LOGGER.info("Inserting a new Assigned Program : {}", assignedProgram.toString());
    assignedProgramMongoCollection.insertOne(assignedProgram);
    return AssignedProgram.builder()
        .id(assignedProgram.getId())
        .creationDate(assignedProgram.getCreationDate())
        .lastModificationDate(assignedProgram.getLastModificationDate())
        .enrollmentDate(assignedProgram.getEnrollmentDate())
        .startDate(assignedProgram.getStartDate())
        .endDate(assignedProgram.getEndDate())
        .program(assignedProgram.getProgram())
        .build();
  }

  @Override
  public AssignedProgram findAssignedProgramById(UUID assignedProgramId) {
    LOGGER.info("Retrieving Assigned Program. Id : {}", assignedProgramId.toString());
    return assignedProgramMongoCollection.find(eq("id", assignedProgramId.toString())).first();
  }

  @Override
  public void deleteAssignedProgram(UUID assignedProgramId) {
    LOGGER.info("Deleting Assigned Program. Id : {}", assignedProgramId);
    assignedProgramMongoCollection.deleteOne(eq("id", assignedProgramId));
  }
}
