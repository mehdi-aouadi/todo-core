package com.m9i.repositories;

import com.m9i.model.Task;
import com.m9i.model.User;

import java.util.List;

public interface TaskRepository {

    Task getTaskByAssignee(User user);

    void saveTask(Task task);

    List<Task> finAllTasks();

}
