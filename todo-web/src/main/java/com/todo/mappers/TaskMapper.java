package com.todo.mappers;

import com.todo.contents.TaskContent;
import com.todo.model.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

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

    default List<TaskContent> taskListToContentList(List<Task> taskList) {
        return taskList.stream().map(task -> taskToContent(task)).collect(Collectors.toList());
    }

    default List<Task> contentListToTaskList(List<TaskContent> contentsList) {
        return contentsList.stream().map(taskContent -> contentToTask(taskContent)).collect(Collectors.toList());
    }

}
