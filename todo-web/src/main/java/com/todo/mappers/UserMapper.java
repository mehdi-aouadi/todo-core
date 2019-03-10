package com.todo.mappers;

import com.todo.contents.UserContent;
import com.todo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {UserProfileMapper.class, UserHistoryMapper.class, AssignedProgramMapper.class}
)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserContent contentToDomain(User user);

  User domainToContent(UserContent userContent);

}
