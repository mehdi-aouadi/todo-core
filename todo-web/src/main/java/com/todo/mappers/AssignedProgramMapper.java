package com.todo.mappers;

import com.todo.contents.AssignedProgramContent;
import com.todo.model.AssignedProgram;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
    uses = ProgramMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AssignedProgramMapper {

  AssignedProgramMapper INSTANCE = Mappers.getMapper(AssignedProgramMapper.class);

  AssignedProgramContent domainToContent(AssignedProgram assignedProgram);
  List<AssignedProgramContent> domainListToContentList(List<AssignedProgram> assignedProgramList);

  AssignedProgram contentToDomain(AssignedProgramContent assignedProgramContent);
  List<AssignedProgram> contentListToDomainList(List<AssignedProgramContent> assignedProgramContentList);

}
