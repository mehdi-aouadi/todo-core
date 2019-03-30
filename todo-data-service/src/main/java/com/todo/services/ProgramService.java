package com.todo.services;

import com.todo.model.Program;
import com.todo.repositories.impl.queries.ProgramQuery;

import java.util.List;
import java.util.UUID;

public interface ProgramService {

  Program createProgram(Program program);

  Program updateProgram(Program program);

  Program findProgramById(UUID programTemplateId);

  List<Program> findProgramsByQuery(ProgramQuery programQuery);

  void deleteProgramById(UUID programId);

}
