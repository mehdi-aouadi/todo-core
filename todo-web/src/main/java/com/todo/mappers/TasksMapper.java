package com.todo.mappers;

import com.todo.contents.TaskContent;
import com.todo.model.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TasksMapper {

    TasksMapper INSTANCE = Mappers.getMapper(TasksMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "summary", target = "summary"),
            @Mapping(source = "requester.id", target = "requesterId"),
            @Mapping(source = "requester.email", target = "requesterEmail"),
            @Mapping(source = "assignee.id", target = "assigneeId"),
            @Mapping(source = "assignee.email", target = "assigneeEmail"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "wishDate", target = "wishDate"),

    })
    TaskContent taskToContent(Task task);

    @InheritInverseConfiguration
    Task contentToTask(TaskContent taskContent);

}
