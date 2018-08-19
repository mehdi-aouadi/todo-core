package com.todo.services;

import com.todo.model.Task;
import com.todo.exceptions.DataIntegrityException;

import java.util.List;

public interface TaskService {

    List<Task> getAllUserCreatedTasksByEmail(String email);

    void saveTask(Task task) throws DataIntegrityException;

    List<Task> getAllUserAssignedTasksByEmail(String email);

}
