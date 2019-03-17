package com.todo.mappers;

import com.todo.contents.ProgramContent;
import com.todo.model.Program;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    uses = TaskMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProgramMapper {

  ProgramMapper INSTANCE = Mappers.getMapper(ProgramMapper.class);

  ProgramContent domainToContent(Program program);

  List<ProgramContent> domainListToContentList(List<Program> programList);

  Program contentToDomain(ProgramContent programContent);

}
