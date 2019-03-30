package com.todo.services;

import com.todo.model.Task;
import com.todo.repositories.impl.queries.TaskQuery;

import java.util.List;
import java.util.UUID;

public interface TaskService {

  Task insertTask(Task task);

  Task updateTask(Task task);

  Task findTaskById(UUID taskId);

  List<Task> findTasksByQuery(TaskQuery taskQuery);

  void deleteTaskById(UUID taskId);

}
