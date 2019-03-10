package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperationException;
import com.todo.model.Program;
import com.todo.repositories.ProgramRepository;
import com.todo.services.ProgramService;
import com.todo.services.ServiceUtils;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class ProgramServiceImpl implements ProgramService, ServiceUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);
  private ProgramRepository programRepository;

  @Inject
  public ProgramServiceImpl(ProgramRepository programRepository) {
    this.programRepository = programRepository;
  }

  @Override
  public Program saveProgram(Program program) {
    program.setId(checkId(program.getId()));
    checkProgram(program);
    return this.programRepository.saveProgram(program);
  }

  @Override
  public Program findProgramById(UUID programTemplateId) {
    return this.programRepository.findProgramById(programTemplateId);
  }

  @Override
  public List<Program> findProgramsByTitle(String programTemplateName, int skip, int limit) {
    limit = checkLimit(100, limit, LOGGER);
    return this.programRepository.findProgramsByTitle(programTemplateName, skip, limit);
  }

  @Override
  public List<Program> findProgramsByRange(int skip, int limit) {
    limit = checkLimit(100, limit, LOGGER);
    return programRepository.findProgramsByRange(skip, limit);
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
    if (StringUtils.isBlank(program.getTitle())) {
      throw new DataIntegrityException("Title", "Missing Program Title");
    }
  }
}
