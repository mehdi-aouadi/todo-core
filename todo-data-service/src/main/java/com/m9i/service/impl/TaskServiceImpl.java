package com.m9i.service.impl;

import com.m9i.model.Task;
import com.m9i.model.User;
import com.m9i.repositories.TaskRepository;
import com.m9i.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepositoryMongoDb;

    public Task getTask(User user) {
        return taskRepositoryMongoDb.getTaskByAssignee(user);
    }

    public void saveTask(Task task) {
        taskRepositoryMongoDb.saveTask(task);
    }
}
