package com.todo.mappers;

import com.todo.common.Page;
import com.todo.contents.TaskContent;
import com.todo.contents.TaskPageContent;
import com.todo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskContent domainToContent(Task task);

  Task contentToDomain(TaskContent taskContent);

  List<TaskContent> domainListToContentList(List<Task> taskList);

  List<Task> contentListToDomainList(List<TaskContent> contentsList);

  @Mappings( {
          @Mapping(source = "totalElementCount", target = "totalCount"),
          @Mapping(source = "content", target = "tasks")
  })
  TaskPageContent domainPageToContentPage(Page<Task> page);

}
