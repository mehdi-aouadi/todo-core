package com.todo.service;

import com.todo.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllUserCreatedTasksByEmail(String email);

    void saveTask(Task task);

    List<Task> getAllUserAssignedTasksByEmail(String email);

}
