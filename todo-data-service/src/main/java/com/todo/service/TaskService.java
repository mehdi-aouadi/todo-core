package com.todo.service;

import com.todo.model.Task;
import exceptions.DataIntegrityException;

import java.util.List;

public interface TaskService {

    List<Task> getAllUserCreatedTasksByEmail(String email);

    void saveTask(Task task) throws DataIntegrityException;

    List<Task> getAllUserAssignedTasksByEmail(String email);

}
