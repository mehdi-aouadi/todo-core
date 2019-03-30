package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.model.Program;
import com.todo.repositories.impl.queries.ProgramQuery;

import java.util.List;
import java.util.UUID;

public interface ProgramRepository {

  Program insertProgram(Program program);

  Program updateProgram(Program program);

  Program findProgramById(UUID programId);

  List<Program> findProgramsByQuery(ProgramQuery programQuery);

  DeleteResult deleteProgramById(UUID programId);

}
