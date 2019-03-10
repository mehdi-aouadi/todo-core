package com.todo.mappers;

import com.todo.contents.UserProfileContent;
import com.todo.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProfileMapper {

  UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

  UserProfile contentToDomain(UserProfileContent userProfileContent);
  UserProfileContent domainToContent(UserProfile userProfile);

}
