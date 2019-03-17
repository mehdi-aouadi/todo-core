package com.todo.mappers;

import com.todo.contents.TaskContent;
import com.todo.model.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskContent domainToContent(Task task);

  Task contentToDomain(TaskContent taskContent);

  List<TaskContent> domainToContent(List<Task> taskList);

  List<Task> contentToDomain(List<TaskContent> contentsList);

}
