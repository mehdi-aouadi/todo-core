package com.todo.repositories;

import com.todo.model.ProgramTemplate;

import java.util.List;
import java.util.UUID;

public interface ProgramTemplateRepository {

  ProgramTemplate saveProgramTemplate(ProgramTemplate programTemplate);

  ProgramTemplate findProgramTemplateById(UUID programTemplateId);

  ProgramTemplate findProgramTemplateByName(String name);

  List<ProgramTemplate> findProgramTemplatesByRange(int skip, int limit);

}
