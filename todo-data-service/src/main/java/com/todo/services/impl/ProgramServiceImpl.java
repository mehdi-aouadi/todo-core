package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.exceptions.DataIntegrityException;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class ProgramServiceImpl implements ProgramService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);
  private ProgramRepository programRepository;

  @Inject
  public ProgramServiceImpl(ProgramRepository programRepository) {
    this.programRepository = programRepository;
  }

  @Override
  public Program createProgram(Program program) {
    if(program.getId() != null) {
      throw new DataIntegrityException(
          "programId", "To create a new program programId must be null"
      );
    } else {
      checkProgram(program);
      program.setId(UUID.randomUUID());
      program.setCreationDate(Instant.now());
      program.setLastModificationDate(Instant.now());
      return this.programRepository.insertProgram(program);
    }
  }

  @Override
  public Program updateProgram(Program program) {
    if(program.getId() == null) {
      throw new DataIntegrityException("programId", "To update a program programId is mandatory");
    } else {
      checkProgram(program);
      program.setLastModificationDate(Instant.now());
      return this.programRepository.updateProgram(program);
    }
  }

  @Override
  public Program findProgramById(UUID programTemplateId) {
    return this.programRepository.findProgramById(programTemplateId);
  }

  @Override
  public Page<Program> findProgramsByQuery(ProgramQuery programQuery) {
    return this.programRepository.find(programQuery);
  }

  @Override
  public void deleteProgramById(UUID programId) {
    DeleteResult deleteResult = programRepository.deleteProgramById(programId);
    if(!deleteResult.wasAcknowledged() || deleteResult.getDeletedCount() == 0) {
      throw new DataOperationException("programId",
          "Unable to delete program with id " + programId.toString());
    }
  }

  private void checkProgram(Program program) {
    if (program == null) {
      throw new IllegalArgumentException("Program is null.");
    }
    if (StringUtils.isBlank(program.getName())) {
      throw new DataIntegrityException("Name", "Missing Program Name");
    }
  }
}
