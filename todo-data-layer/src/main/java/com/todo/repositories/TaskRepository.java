package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.model.Task;
import com.todo.repositories.impl.queries.TaskQuery;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

  Task insertTask(Task task);

  Task updateTask(Task task);

  Task findTaskById(UUID taskId);

  List<Task> findTasksByQuery(TaskQuery taskQuery);

  DeleteResult deleteTaskById(UUID taskId);

}
