package com.m9i.mappers;

import com.m9i.contents.UserContent;
import com.m9i.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phoneNumber", target = "phoneNumber")
    })
    UserContent userToContent(User user);

    @InheritInverseConfiguration
    User contentToUser(UserContent userContent);

}
