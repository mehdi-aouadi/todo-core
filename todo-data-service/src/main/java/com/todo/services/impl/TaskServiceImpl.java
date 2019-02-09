package com.todo.services.impl;

import com.google.inject.Inject;
import com.todo.exceptions.DataIntegrityException;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.services.TaskService;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
public class TaskServiceImpl implements TaskService {

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
    checkTask(task);
    if (task.getId() == null) {
      task.setId(UUID.randomUUID());
    }
    return taskRepository.saveTask(task);
  }

  @Override
  public Task findTaskById(UUID taskId) {
    return taskRepository.findTaskById(taskId);
  }

  @Override
  public List<Task> findTasksByName(String taskName, int skip, int limit) {
    return taskRepository.findTasksByName(taskName, skip, limit);
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
