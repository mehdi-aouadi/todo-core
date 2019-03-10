package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
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

  private static final ReplaceOptions REPLACE_OPTIONS
      = ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true));

  private final MongoCollection<Task> taskMongoCollection
      = DbManager.getMongoCollection(Task.class);

  @Override
  public Task findTaskById(UUID taskId) {
    LOGGER.info("Retrieving Tasks by id {}.", taskId.toString());
    return taskMongoCollection.find(eq("id", taskId.toString())).first();
  }

  @Override
  public Task saveTask(Task task) {
    LOGGER.info("Inserting new Task : {}", task.toString());
    taskMongoCollection.replaceOne(eq("id", task.getId().toString()), task, REPLACE_OPTIONS);
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
    return taskMongoCollection.find()
        .sort(Sorts.ascending("lastModificationDate"))
        .skip(skip)
        .limit(limit)
        .into(new ArrayList<>());
  }

  @Override
  public List<Task> findTasksByName(String taskName, int skip, int limit) {
    LOGGER.info("Retrieving Tasks by name : {}. Skip {}, limit {}", taskName, skip, limit);

    return taskMongoCollection.find(regex("name", taskName))
        .sort(Sorts.ascending("name"))
        .skip(skip)
        .limit(limit)
        .into(new ArrayList<>());

  }

  @Override
  public DeleteResult deleteTaskById(UUID taskId) {
    LOGGER.info("Deleting Task. Id : {}", taskId.toString());
    return taskMongoCollection.deleteOne(eq("id", taskId.toString()));
  }
}
