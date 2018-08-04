package com.todo.service.impl;

import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<Task> getAllUserCreatedTasksByEmail(String email) {
        return taskRepository.finAllUserTasksByEmail(email);
    }

    @Override
    public void saveTask(Task task) {
        if (task.getId() == null || task.getId().isEmpty()) {
            task.setId(UUID.randomUUID().toString());
        }
        taskRepository.saveTask(task);
    }

    @Override
    public List<Task> getAllUserAssignedTasksByEmail(String email) {
        return taskRepository.findTasksByAssignee(email);
    }
}
