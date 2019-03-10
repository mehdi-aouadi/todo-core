package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

  Task saveTask(Task task);

  Task findTaskById(UUID taskId);

  List<Task> findTasksByRange(int skip, int limit);

  List<Task> findTasksByName(String taskName, int skip, int limit);

  DeleteResult deleteTaskById(UUID taskId);

}
