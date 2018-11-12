package com.todo.services;

import com.todo.model.ProgramTemplate;

import java.util.List;
import java.util.UUID;

public interface ProgramTemplateService {

  ProgramTemplate saveProgramTemplate(ProgramTemplate programTemplate);
  ProgramTemplate getProgramTemplateById(UUID programTemplateId);
  ProgramTemplate getProgramTemplateByName(String programTemplateName);
  List<ProgramTemplate> getProgramTemplateByRange(int skip, int limit);

}
