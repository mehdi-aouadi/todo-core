package com.todo.services;

import com.todo.exceptions.DataIntegrityException;
import com.todo.model.Task;

import java.util.List;

public interface TaskService {

  List<Task> getAllUserCreatedTasksByEmail(String email);

  Task saveTask(Task task) throws DataIntegrityException;

  List<Task> getAllUserAssignedTasksByEmail(String email);

}
