package com.todo.mappers;

import com.todo.contents.ProgramContent;
import com.todo.model.Program;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = TaskMapper.class)
public interface ProgramMapper {

  ProgramMapper INSTANCE = Mappers.getMapper(ProgramMapper.class);

  @Mappings({
          @Mapping(source = "id", target = "id"),
          @Mapping(source = "title", target = "title"),
          @Mapping(source = "description", target = "description"),
          @Mapping(source = "introduction", target = "introduction"),
          @Mapping(source = "taskList", target = "taskList"),
          @Mapping(source = "duration", target = "duration")
  })
  ProgramContent programTemplateToContent(Program program);

  default List<ProgramContent> programTemplateListToContents(
      List<Program> programList) {
    return programList.stream()
        .map(programTemplate ->
            programTemplateToContent(programTemplate))
        .collect(Collectors.toList());
  }

  @InheritInverseConfiguration
  Program contentToProgramTemplate(ProgramContent programContent);

}
