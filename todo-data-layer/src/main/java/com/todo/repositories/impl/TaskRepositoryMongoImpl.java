package com.todo.repositories.impl;

import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryMongoImpl implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryMongoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Task> findTasksByAssignee(String email) {
        LOGGER.info("Retrieving Tasks assigned to {} from db.", email);
        Query query = new Query();
        query.addCriteria(Criteria.where("assignee.email").is(email));
        return mongoTemplate.find(query, Task.class);
    }

    @Override
    public void saveTask(Task task) {
        LOGGER.info("Inserting new Task assigned to {}. Description : {}.", task.getAssignee(), task.getDescription());
        mongoTemplate.save(task);
    }

    @Override
    public List<Task> finAllUserTasksByEmail(String email) {
        LOGGER.info("Retrieving all user {} tasks.", email);
        Query query = new Query();
        query.addCriteria(Criteria.where("requester.email").is(email));
        return mongoTemplate.find(query, Task.class);
    }
}
