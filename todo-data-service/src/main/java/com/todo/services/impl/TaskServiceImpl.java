package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperationException;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.services.ServiceUtils;
import com.todo.services.TaskService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class TaskServiceImpl implements TaskService, ServiceUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
  private TaskRepository taskRepository;

  @Inject
  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public List<Task> findTasksByRange(int skip, int limit) {
    return taskRepository.findTasksByRange(skip, limit);
  }

  @Override
  public Task saveTask(Task task) throws DataIntegrityException {
    task.setId(checkId(task.getId()));
    checkTask(task);
    return taskRepository.saveTask(task);
  }

  @Override
  public Task findTaskById(UUID taskId) {
    return taskRepository.findTaskById(taskId);
  }

  @Override
  public List<Task> findTasksByName(String taskName, int skip, int limit) {
    limit = checkLimit(100, limit, LOGGER);
    return taskRepository.findTasksByName(taskName, skip, limit);
  }

  @Override
  public void deleteTaskById(UUID taskId) {
    DeleteResult deleteResult = taskRepository.deleteTaskById(taskId);
    if(!deleteResult.wasAcknowledged() || deleteResult.getDeletedCount() == 0) {
      throw new DataOperationException("taskId",
          "Unable to delete task with id " + taskId.toString());
    }
  }

  private void checkTask(Task task) throws DataIntegrityException {
    if (task == null) {
      throw new IllegalArgumentException("Task is null.");
    }
    if (task.getName() == null || StringUtils.isBlank(task.getName())) {
      throw new DataIntegrityException("Task Name", "Missing Task Name.");
    }
  }
}
