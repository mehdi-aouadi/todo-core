package com.m9i.repositories;

import com.m9i.model.Task;
import com.m9i.model.User;

import java.util.List;

public interface TaskRepository {

    List<Task> findTasksByAssignee(String email);

    void saveTask(Task task);

    List<Task> finAllUserTasksByEmail(String email);

}
