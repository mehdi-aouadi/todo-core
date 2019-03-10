package com.todo.mappers;

import com.todo.contents.UserHistoryContent;
import com.todo.model.UserHistory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = AssignedProgramMapper.class
)
public interface UserHistoryMapper {

  UserHistoryMapper INSTANCE = Mappers.getMapper(UserHistoryMapper.class);

  UserHistory contentToDomain(UserHistoryContent userHistoryContent);
  UserHistoryContent domainToContent(UserHistory userHistory);

}
