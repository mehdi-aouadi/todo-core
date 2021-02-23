package com.todo.mappers;

import com.todo.common.Page;
import com.todo.contents.TaskContent;
import com.todo.contents.TaskPageContent;
import com.todo.model.Task;

import java.util.List;

public class TaskMapperDecorator implements TaskMapper {

    private TaskMapper delegate = TaskMapper.INSTANCE;

    @Override
    public TaskContent domainToContent(Task task) {
        return delegate.domainToContent(task);
    }

    @Override
    public Task contentToDomain(TaskContent taskContent) {
        return delegate.contentToDomain(taskContent);
    }

    @Override
    public List<TaskContent> domainListToContentList(List<Task> taskList) {
        return delegate.domainListToContentList(taskList);
    }

    @Override
    public List<Task> contentListToDomainList(List<TaskContent> contentsList) {
        return delegate.contentListToDomainList(contentsList);
    }

    @Override
    public TaskPageContent domainPageToContentPage(Page<Task> page) {
        TaskPageContent taskPageContent = delegate.domainPageToContentPage(page);
        taskPageContent.setTasks(domainListToContentList(page.getContent()));
        return taskPageContent;
    }
}
