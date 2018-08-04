package com.m9i.service;

import com.m9i.model.Task;
import com.m9i.model.User;

import java.util.List;

public interface TaskService {

    List<Task> getAllUserCreatedTasksByEmail(String email);

    void saveTask(Task task);

    List<Task> getAllUserAssignedTasksByEmail(String email);

}
