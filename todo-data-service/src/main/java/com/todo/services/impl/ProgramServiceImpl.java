package com.todo.services.impl;

import com.google.inject.Inject;
import com.todo.exceptions.DataIntegrityException;
import com.todo.model.Program;
import com.todo.repositories.ProgramRepository;
import com.todo.services.ProgramService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class ProgramServiceImpl implements ProgramService {

  private ProgramRepository programRepository;

  @Inject
  public ProgramServiceImpl(ProgramRepository programRepository) {
    this.programRepository = programRepository;
  }

  @Override
  public Program saveProgram(Program program) {
    checkProgram(program);
    if(program.getId() == null) {
      program.setId(UUID.randomUUID());
    }
    return this.programRepository.saveProgram(program);
  }

  @Override
  public Program findProgramById(UUID programTemplateId) {
    return this.programRepository.findProgramById(programTemplateId);
  }

  @Override
  public List<Program> findProgramsByTitle(String programTemplateName, int skip, int limit) {
    return this.programRepository.findProgramsByTitle(programTemplateName, skip, limit);
  }

  @Override
  public List<Program> findProgramsByRange(int skip, int limit) {
    return programRepository.findProgramsByRange(skip, limit);
  }

  private void checkProgram(Program program) {
    if(StringUtils.isBlank(program.getTitle())) {
      throw new DataIntegrityException("Title", "Missing Program Name");
    }
  }
}
