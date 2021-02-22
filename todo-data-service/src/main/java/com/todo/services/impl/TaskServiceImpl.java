package com.todo.services.impl;

import com.google.inject.Inject;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperation;
import com.todo.exceptions.DataOperationException;
import com.todo.exceptions.PersistenceException;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.impl.queries.TaskQuery;
import com.todo.services.TaskService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public class TaskServiceImpl implements TaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(com.todo.services.TaskService.class);

  private static final String TASK_ENTITY_NAME = Task.class.getSimpleName();
  private static final String TASK_ID_FIELD_NAME = "taskId";
  private static final String TASK_NAME_FIELD_NAME = "taskName";

  private TaskRepository taskRepository;

  @Inject
  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public Page<Task> find(TaskQuery taskQuery) {
    return taskRepository.find(taskQuery);
  }

  @Override
  public Task insert(Task task) throws DataIntegrityException {
    checkTaskForCreation(task);
    task.setId(UUID.randomUUID());
    task.setCreationDate(Instant.now());
    task.setLastModificationDate(Instant.now());
    Task insertedTask = taskRepository.insert(task);
    if(insertedTask == null) {
      LOGGER.error("Unable to insert Task {} due to internal server error.", task.toString());
      throw DataOperationException.builder()
              .dataOperation(DataOperation.INSERT)
              .entityName(TASK_ENTITY_NAME)
              .message("Unable to create Task due to internal server error. Task : " + task.toString())
              .build();
    } else {
      return insertedTask;
    }
  }

  @Override
  public Task update(Task task) throws DataIntegrityException {
    checkTaskForUpdate(task);
    task.setId(UUID.randomUUID());
    task.setLastModificationDate(Instant.now());
    return taskRepository.update(task);
  }

  @Override
  public Optional<Task> findById(UUID taskId) {
    return taskRepository.findById(taskId);
  }

  @Override
  public void deleteById(UUID taskId) {
    DeleteResult deleteResult = taskRepository.deleteById(taskId);
    if(!deleteResult.wasAcknowledged() || deleteResult.getDeletedCount() == 0) {
      throw DataOperationException.builder()
              .dataOperation(DataOperation.DELETE)
              .entityName(TASK_ENTITY_NAME)
              .message("Unable to delete Task with id "
                      + taskId.toString()
                      + " due to internal server error"
              ).build();
    }
  }

  private void checkTaskForCreation(Task task) throws DataIntegrityException {
    if (task == null) {
      LOGGER.error("Unable to create a new Task. Value is null");
      throw PersistenceException.builder()
              .entityName(TASK_ENTITY_NAME)
              .message("Cannot create Task. Value is null")
              .build();
    }
    if (task.getId() != null) {
      LOGGER.error("Unable to create a new Task {} . Id must be null", task.toString());
      throw DataIntegrityException.builder()
              .entityName(TASK_ENTITY_NAME)
              .fieldName(TASK_ID_FIELD_NAME)
              .message("To create a new Task id must be null")
              .build();
    }
    if (task.getName() == null || StringUtils.isBlank(task.getName())) {
      LOGGER.error("Unable to create a new Task {} . The name field is mandatory", task.toString());
      throw DataIntegrityException.builder()
              .entityName(TASK_ENTITY_NAME)
              .fieldName(TASK_NAME_FIELD_NAME)
              .message("To create a new Task the name field is mandatory")
              .build();
    }
  }

  private void checkTaskForUpdate(Task task) throws DataIntegrityException {
    if (task == null) {
      LOGGER.error("Unable to update Task. Value is null");
      throw PersistenceException.builder()
              .entityName(TASK_ENTITY_NAME)
              .message("Cannot update Task. Value is null")
              .build();
    }
    if (task.getName() == null || StringUtils.isBlank(task.getName())) {
      LOGGER.error("Unable to create a new Task {} . The name field is mandatory", task.toString());
      throw DataIntegrityException.builder()
              .entityName(TASK_ENTITY_NAME)
              .fieldName(TASK_NAME_FIELD_NAME)
              .message("To create a new Task the name field is mandatory")
              .build();
    }
  }
}
