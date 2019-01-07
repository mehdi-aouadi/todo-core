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
  public List<Task> getAllUserCreatedTasksByEmail(String email) {
    return taskRepository.finAllUserTasksByEmail(email);
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
  public List<Task> getAllUserAssignedTasksByEmail(String email) {
    return taskRepository.findTasksByAssignee(email);
  }

  private void checkTask(Task task) throws DataIntegrityException {
    if (task == null) {
      throw new IllegalArgumentException("Task is null.");
    }
    if (task.getAssignee() == null || StringUtils.isBlank(task.getAssignee().getEmail())) {
      throw new DataIntegrityException("Assignee Email", "Missing assignee email.");
    }
    if (task.getRequester() == null || StringUtils.isBlank(task.getRequester().getEmail())) {
      throw new DataIntegrityException("Requester Email", "Missing requester email.");
    }
    if (StringUtils.isBlank(task.getSummary())) {
      throw new DataIntegrityException("Task Summary", "Missing task summary.");
    }
  }
}
