package com.todo.mappers;

import com.todo.common.Page;
import com.todo.contents.AssignedProgramContent;
import com.todo.contents.AssignedProgramPageContent;
import com.todo.model.AssignedProgram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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

  @Mappings( {
          @Mapping(source = "totalElementCount", target = "totalCount"),
          @Mapping(source = "content", target = "assignedPrograms")
  })
  AssignedProgramPageContent domainPageToContentPage(Page<AssignedProgram> page);

}
