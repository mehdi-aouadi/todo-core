package com.todo.services;

import com.todo.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

  Task saveTask(Task task);

  Task findTaskById(UUID taskId);

  List<Task> findTasksByRange(int skip, int limit);

  List<Task> findTasksByName(String taskName, int skip, int limit);

  void deleteTaskById(UUID taskId);

}
