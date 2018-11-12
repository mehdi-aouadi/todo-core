package com.todo.services.impl;

import com.google.inject.Inject;
import com.todo.exceptions.DataIntegrityException;
import com.todo.model.ProgramTemplate;
import com.todo.repositories.ProgramTemplateRepository;
import com.todo.services.ProgramTemplateService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class ProgramTemplateServiceImpl implements ProgramTemplateService {

  private ProgramTemplateRepository programTemplateRepository;

  @Inject
  public ProgramTemplateServiceImpl(ProgramTemplateRepository programTemplateRepository) {
    this.programTemplateRepository = programTemplateRepository;
  }

  @Override
  public ProgramTemplate saveProgramTemplate(ProgramTemplate programTemplate) {
    checkProgramTepate(programTemplate);
    if(programTemplate.getId() == null) {
      programTemplate.setId(UUID.randomUUID());
    }
    return this.programTemplateRepository.saveProgramTemplate(programTemplate);
  }

  @Override
  public ProgramTemplate getProgramTemplateById(UUID programTemplateId) {
    return this.programTemplateRepository.findProgramTemplateById(programTemplateId);
  }

  @Override
  public ProgramTemplate getProgramTemplateByName(String programTemplateName) {
    return this.programTemplateRepository.findProgramTemplateByName(programTemplateName);
  }

  @Override
  public List<ProgramTemplate> getProgramTemplateByRange(int skip, int limit) {
    return programTemplateRepository.findProgramTemplatesByRange(skip, limit);
  }

  private void checkProgramTepate(ProgramTemplate programTemplate) {
    if(StringUtils.isBlank(programTemplate.getName())) {
      throw new DataIntegrityException("Name", "Missing Program Template Name");
    }
    if(programTemplate.getPeriod() == null) {
      throw new DataIntegrityException("Period", "Missing Program Template Period");
    }
  }
}
