package com.todo.services;

import com.todo.model.Program;

import java.util.List;
import java.util.UUID;

public interface ProgramService {

  Program createProgram(Program program);

  Program updateProgram(Program program);

  Program findProgramById(UUID programTemplateId);

  List<Program> findProgramsByTitle(String programTemplateName, int skip, int limit);

  List<Program> findProgramsByRange(int skip, int limit);

  void deleteProgramById(UUID programId);

}
