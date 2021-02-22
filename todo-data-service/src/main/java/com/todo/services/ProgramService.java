package com.todo.services;

import com.todo.common.Page;
import com.todo.model.Program;
import com.todo.repositories.impl.queries.ProgramQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgramService {

  Program insert(Program program);

  Program update(Program program);

  Optional<Program> findById(UUID programTemplateId);

  Page<Program> findByQuery(ProgramQuery programQuery);

  void deleteById(UUID programId);

}
