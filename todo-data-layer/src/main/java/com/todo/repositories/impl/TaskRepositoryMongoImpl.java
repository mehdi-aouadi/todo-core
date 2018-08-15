package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.todo.dbutils.DbManager;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
public class TaskRepositoryMongoImpl implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryMongoImpl.class);

    private final MongoCollection<Task> mongoCollection = DbManager.getMongoCollection(Task.class);

    @Override
    public List<Task> findTasksByAssignee(String email) {
        LOGGER.info("Retrieving Tasks assigned to {} from db.", email);
        return mongoCollection.find(eq("assignee.email", email)).into(new ArrayList<>());
    }

    @Override
    public void saveTask(Task task) {
        LOGGER.info("Inserting new Task assigned to {}. Description : {}.", task.getAssignee(), task.getDescription());
        mongoCollection.insertOne(task);
    }

    @Override
    public List<Task> finAllUserTasksByEmail(String email) {
        LOGGER.info("Retrieving all user {} tasks.", email);
        return mongoCollection.find(eq("requester.email", email)).into(new ArrayList<>());
    }
}
