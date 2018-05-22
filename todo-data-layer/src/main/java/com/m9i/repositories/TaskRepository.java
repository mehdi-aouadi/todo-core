package com.m9i.repositories;

import com.m9i.domain.Task;
import com.m9i.domain.User;

import java.util.List;

public interface TaskRepository {

    Task getTaskByAssignee(User user);

    void saveTask(Task task);

    List<Task> finAllTasks();

}
