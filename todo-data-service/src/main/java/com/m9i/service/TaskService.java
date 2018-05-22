package com.m9i.service;

import com.m9i.domain.Task;
import com.m9i.domain.User;

public interface TaskService {

    Task getTask(User user);

    void saveTask(Task task);

}
