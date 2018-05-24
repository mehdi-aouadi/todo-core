package com.m9i.repositories.impl;

import com.m9i.model.Task;
import com.m9i.model.User;
import com.m9i.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepositoryMongoImpl implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryMongoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Task getTaskByAssignee(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        LOGGER.info("Retrieving Task assigned to {} from db", user.getEmail());
        return mongoTemplate.findOne(query, Task.class);
    }

    @Override
    public void saveTask(Task task) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(task.getId()));

        Update update = new Update();
        update.set("_id", UUID.randomUUID().toString());
        update.set("summary", task.getSummary());
        update.set("description", task.getDescription());
        update.set("assignee", task.getAssignee());

        LOGGER.info("Upserting task : {}", task.getDescription());

        mongoTemplate.upsert(query, update, Task.class.getSimpleName());
    }

    @Override
    public List<Task> finAllTasks() {
        throw new NotImplementedException();
    }
}
