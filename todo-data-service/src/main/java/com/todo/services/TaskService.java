package com.todo.services;

import com.todo.common.Page;
import com.todo.model.Task;
import com.todo.repositories.impl.queries.TaskQuery;

import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    Task insert(Task task);

    Task update(Task task);

    Optional<Task> findById(UUID taskId);

    Page<Task> find(TaskQuery taskQuery);

    void deleteById(UUID taskId);
}
