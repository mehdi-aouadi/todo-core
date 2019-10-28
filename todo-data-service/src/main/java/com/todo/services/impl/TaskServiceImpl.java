package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperationException;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.impl.queries.TaskQuery;
import com.todo.services.TaskService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class TaskServiceImpl implements TaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
  private TaskRepository taskRepository;

  @Inject
  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public List<Task> findTasksByQuery(TaskQuery taskQuery) {
    return taskRepository.findTasksByQuery(taskQuery);
  }

  @Override
  public Task createTask(Task task) throws DataIntegrityException {
    if(task.getId() != null) {
      throw new DataIntegrityException("taskId", "To create a new Task taskId must be null.");
    } else {
      checkTask(task);
      task.setId(UUID.randomUUID());
      task.setCreationDate(Instant.now());
      task.setLastModificationDate(Instant.now());
      return taskRepository.insertTask(task);
    }
  }

  @Override
  public Task updateTask(Task task) throws DataIntegrityException {
    if(task.getId() == null) {
      throw new DataIntegrityException("taskId", "To update a Task taskId is mandatory");
    } else {
      checkTask(task);
      task.setId(UUID.randomUUID());
      task.setLastModificationDate(Instant.now());
      return taskRepository.updateTask(task);
    }
  }

  @Override
  public Task findTaskById(UUID taskId) {
    return taskRepository.findTaskById(taskId);
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
