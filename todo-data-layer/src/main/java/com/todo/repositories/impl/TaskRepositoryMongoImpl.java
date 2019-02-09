package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.todo.dbutils.DbManager;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

@NoArgsConstructor
public class TaskRepositoryMongoImpl implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryMongoImpl.class);

    private final MongoCollection<Task> mongoCollection = DbManager.getMongoCollection(Task.class);

    @Override
    public Task findTaskById(UUID id) {
        LOGGER.info("Retrieving Tasks by id {}.", id);
        return mongoCollection.find(eq("id", id)).first();
    }

    @Override
    public Task saveTask(Task task) {
        LOGGER.info("Inserting new Task : {}", task.toString());
        mongoCollection.insertOne(task);
        return Task.builder()
                .id(task.getId())
                .creationDate(task.getCreationDate())
                .lastModificationDate(task.getLastModificationDate())
                .name(task.getName())
                .summary(task.getSummary())
                .description(task.getDescription())
                .duration(task.getDuration())
                .mediaIds(task.getMediaIds())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .build();
    }

    @Override
    public List<Task> findTasksByRange(int skip, int limit) {
        LOGGER.info("Retrieving all tasks. Skip {}, limit {}", skip, limit);
        skip = skip > 100 ? 100 : skip;
        return mongoCollection.find()
                .sort(Sorts.ascending("lastModificationDate"))
                .skip(skip)
                .limit(limit)
                .into(new ArrayList<>());
    }

    @Override
    public List<Task> findTasksByName(String taskName, int skip, int limit) {
        LOGGER.info("Retrieving Tasks by name : {}", taskName);
        if(limit > 100) {
            limit = 100;
            LOGGER.warn("Limit exceeded. Set to default value {}.", limit);
        }

        return mongoCollection.find(regex("name", taskName))
                .sort(Sorts.ascending("name"))
                .skip(skip)
                .limit(limit)
                .into(new ArrayList<>());

    }
}
