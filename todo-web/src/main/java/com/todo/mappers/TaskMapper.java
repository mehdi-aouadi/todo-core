package com.todo.mappers;

import com.todo.contents.TaskContent;
import com.todo.model.Task;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskContent mapFromDomain(Task task);

  Task mapToDomain(TaskContent taskContent);

  List<TaskContent> mapFromDomain(List<Task> taskList);

  List<Task> mapToDomain(List<TaskContent> contentsList);

}
