package com.todo.mappers;

import com.todo.contents.ProgramTemplateContent;
import com.todo.model.ProgramTemplate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = TaskMapper.class)
public interface ProgramTemplateMapper {

  ProgramTemplateMapper INSTANCE = Mappers.getMapper(ProgramTemplateMapper.class);

  @Mappings({
      @Mapping(source = "id", target = "id"),
      @Mapping(source = "period", target = "period"),
      @Mapping(source = "name", target = "name"),
      @Mapping(source = "introduction", target = "introduction"),
      @Mapping(source = "description", target = "description"),
      @Mapping(source = "taskList", target = "taskContentList")
  })
  ProgramTemplateContent programTemplateToContent(ProgramTemplate programTemplate);

  default List<ProgramTemplateContent> programTemplateListToContents(
      List<ProgramTemplate> programTemplateList) {
    return programTemplateList.stream()
        .map(programTemplate ->
            programTemplateToContent(programTemplate))
        .collect(Collectors.toList());
  }

  @InheritInverseConfiguration
  ProgramTemplate contentToProgramTemplate(ProgramTemplateContent programTemplateContent);

}
