package com.m9i.service;

import com.m9i.model.Task;
import com.m9i.model.User;

public interface TaskService {

    Task getTask(User user);

    void saveTask(Task task);

}
