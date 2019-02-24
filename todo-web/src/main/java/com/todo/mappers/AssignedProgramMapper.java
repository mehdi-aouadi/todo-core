package com.todo.mappers;

import com.todo.contents.AssignedProgramContent;
import com.todo.model.AssignedProgram;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProgramMapper.class)
public interface AssignedProgramMapper {

  AssignedProgramMapper INSTANCE = Mappers.getMapper(AssignedProgramMapper.class);

  AssignedProgramContent mapFromDomain(AssignedProgram assignedProgram);

  AssignedProgram mapFromContent(AssignedProgramContent assignedProgramContent);

}
