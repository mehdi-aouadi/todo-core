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

  @Mappings( {
      @Mapping(source = "id", target = "id"),
      @Mapping(source = "name", target = "name"),
      @Mapping(source = "description", target = "description"),
      @Mapping(source = "duration", target = "duration"),
      @Mapping(source = "mediaIds", target = "mediaIds"),
      @Mapping(source = "startDate", target = "startDate"),
      @Mapping(source = "endDate", target = "endDate")
  })
  TaskContent taskToContent(Task task);

  @InheritInverseConfiguration
  Task contentToTask(TaskContent taskContent);

  default List<TaskContent> taskListToContentList(List<Task> taskList) {
    return taskList.stream().map(task -> taskToContent(task)).collect(Collectors.toList());
  }

  default List<Task> contentListToTaskList(List<TaskContent> contentsList) {
    return contentsList.stream().map(taskContent -> contentToTask(taskContent))
        .collect(Collectors.toList());
  }

}
