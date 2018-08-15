package com.todo.service.impl;

import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import exceptions.DataIntegrityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<Task> getAllUserCreatedTasksByEmail(String email) {
        return taskRepository.finAllUserTasksByEmail(email);
    }

    @Override
    public void saveTask(Task task) throws DataIntegrityException {
        checkTask(task);
        if (task.getId() == null || task.getId().isEmpty()) {
            task.setId(UUID.randomUUID().toString());
        }
        taskRepository.saveTask(task);
    }

    @Override
    public List<Task> getAllUserAssignedTasksByEmail(String email) {
        return taskRepository.findTasksByAssignee(email);
    }

    private void checkTask(Task task) throws DataIntegrityException {
        if(task == null) {
            throw new IllegalArgumentException("Task is null.");
        }
        if(task.getAssignee() == null || StringUtils.isBlank(task.getAssignee().getEmail())) {
            throw new DataIntegrityException("Missing assignee email.");
        }
        if(task.getRequester() == null || StringUtils.isBlank(task.getRequester().getEmail())) {
            throw new DataIntegrityException("Missing requester email.");
        }
        if(StringUtils.isBlank(task.getSummary())) {
            throw new DataIntegrityException("Missing task summary.");
        }
    }
}
