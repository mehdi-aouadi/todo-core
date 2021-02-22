package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperation;
import com.todo.exceptions.DataOperationException;
import com.todo.model.Program;
import com.todo.repositories.ProgramRepository;
import com.todo.repositories.impl.queries.ProgramQuery;
import com.todo.services.ProgramService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public class ProgramServiceImpl implements ProgramService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);
  private static final String PROGRAM_ENTITY_NAME = Program.class.getSimpleName();
  private static final String PROGRAM_ID_FIELD_NAME = "programId";
  private static final String PROGRAM_NAME_FIELD_NAME = "programName";
  private ProgramRepository programRepository;

  @Inject
  public ProgramServiceImpl(ProgramRepository programRepository) {
    this.programRepository = programRepository;
  }

  @Override
  public Program insert(Program program) {
    checkProgramForCreation(program);
    program.setId(UUID.randomUUID());
    program.setCreationDate(Instant.now());
    program.setLastModificationDate(Instant.now());
    return this.programRepository.insert(program);
  }

  @Override
  public Program update(Program program) {
    checkProgramForUpdate(program);
    program.setLastModificationDate(Instant.now());
    return this.programRepository.update(program);
  }

  @Override
  public Optional<Program> findById(UUID programTemplateId) {
    return this.programRepository.findById(programTemplateId);
  }

  @Override
  public Page<Program> findByQuery(ProgramQuery programQuery) {
    return this.programRepository.find(programQuery);
  }

  @Override
  public void deleteById(UUID programId) {
    DeleteResult deleteResult = programRepository.deleteById(programId);
    if(!deleteResult.wasAcknowledged() || deleteResult.getDeletedCount() == 0) {
      LOGGER.error("Cannot delete program with id {} due to an internal error.", programId);
      throw DataOperationException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .message("Unable to delete program with Id " + programId + " due to internal error")
              .dataOperation(DataOperation.DELETE)
              .build();
    }
  }

  private void checkProgramForCreation(Program program) {
    if (program == null) {
      LOGGER.error("Cannot insert a null program.");
      throw DataOperationException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .dataOperation(DataOperation.INSERT)
              .message("Unable to insert a null program")
              .build();
    }
    if (program.getId() != null) {
      LOGGER.error("To create a new program, the id field must be null. Program {}", program);
      throw DataIntegrityException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .fieldName(PROGRAM_ID_FIELD_NAME)
              .message("To create a new Program id must be null. Program : " + program)
              .build();
    }
    if (StringUtils.isBlank(program.getName())) {
      LOGGER.error("To create a new program, the name field is mandatory. Program {}", program);
      throw DataIntegrityException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .fieldName(PROGRAM_NAME_FIELD_NAME)
              .message("To create a new Program, name is mandatory. Program : " + program)
              .build();
    }
  }

  private void checkProgramForUpdate(Program program) {
    if (program == null) {
      LOGGER.error("Cannot update a program with null");
      throw DataOperationException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .dataOperation(DataOperation.INSERT)
              .message("Unable to update a program with null")
              .build();
    }
    if(program.getId() == null) {
      LOGGER.error("Cannot update a program with null Id. Program {}", program);
      throw DataIntegrityException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .fieldName(PROGRAM_ID_FIELD_NAME)
              .message("To update a program, Id is a mandatory field. Program : " + program)
              .build();
    }
    if (StringUtils.isBlank(program.getName())) {
      LOGGER.error("Cannot update a program without name. Program {}", program);
      throw DataIntegrityException.builder()
              .entityName(PROGRAM_ENTITY_NAME)
              .fieldName(PROGRAM_ID_FIELD_NAME)
              .message("To update a program, the name field is mandatory. Program : " + program)
              .build();
    }
  }
}
