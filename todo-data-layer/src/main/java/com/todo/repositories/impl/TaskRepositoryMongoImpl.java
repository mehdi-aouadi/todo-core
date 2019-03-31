package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.todo.dbutils.MongoDbManager;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.impl.queries.TaskQuery;
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

  private static final String ID_FIELD = "_id";

  private final MongoCollection<Task> taskMongoCollection
      = MongoDbManager.getMongoCollection(Task.class);

  @Override
  public Task findTaskById(UUID taskId) {
    LOGGER.info("Retrieving Tasks by id {}.", taskId.toString());
    return taskMongoCollection.find(eq(ID_FIELD, taskId)).first();
  }

  @Override
  public Task insertTask(Task task) {
    LOGGER.info("Inserting new Task : {}", task.toString());
    taskMongoCollection.insertOne(task);
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
  public Task updateTask(Task task) {
    LOGGER.info("Updating Task : {}", task.toString());
    taskMongoCollection.findOneAndReplace(eq(ID_FIELD, task.getId()), task);
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
  public List<Task> findTasksByQuery(TaskQuery taskQuery) {
    LOGGER.info("Retrieving Tasks by Query : {}", taskQuery);

    return taskMongoCollection.find(taskQuery.toBsonFilter())
        .sort(taskQuery.nameOrderToBson())
        .sort(taskQuery.lastModificationDateOrderToBson())
        .sort(taskQuery.creationDateOrderToBson())
        .skip(taskQuery.getPageIndex())
        .limit(taskQuery.getPageSize())
        .into(new ArrayList<>());

  }

  @Override
  public DeleteResult deleteTaskById(UUID taskId) {
    LOGGER.info("Deleting Task. Id : {}", taskId.toString());
    return taskMongoCollection.deleteOne(eq(ID_FIELD, taskId.toString()));
  }
}
