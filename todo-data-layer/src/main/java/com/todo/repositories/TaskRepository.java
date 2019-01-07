package com.todo.repositories;

import com.todo.model.Task;

import java.util.List;

public interface TaskRepository {

  List<Task> findTasksByAssignee(String email);

  Task saveTask(Task task);

  List<Task> finAllUserTasksByEmail(String email);

}
