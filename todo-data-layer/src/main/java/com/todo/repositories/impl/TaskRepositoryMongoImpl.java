package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.dbutils.MongoDbManager;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.Task;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.queries.TaskQuery;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class TaskRepositoryMongoImpl implements TaskRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepositoryMongoImpl.class);

  private static final String ID_FIELD = "_id";

  private final MongoCollection<Task> taskMongoCollection
      = MongoDbManager.getMongoCollection(Task.class);

  @Override
  public Optional<Task> findById(UUID taskId) {
    LOGGER.info("Retrieving Tasks by id {}.", taskId.toString());
    return Optional.ofNullable(taskMongoCollection.find(eq(ID_FIELD, taskId)).first());
  }

  @Override
  public Task insert(Task task) {
    LOGGER.info("Inserting a new Task : {}", task.toString());
    try {
      taskMongoCollection.insertOne(task);
      return task;
    } catch (Exception e) {
      LOGGER.error("Insert of Task {} failed due to internal error. Stack Trace : {}", task.getName(), e.getStackTrace().toString());
      return null;
    }
  }

  @Override
  public Task update(Task task) throws ResourceNotFoundException {
    LOGGER.info("Updating Task : {}", task.toString());
    return Optional.ofNullable(taskMongoCollection.findOneAndReplace(eq(ID_FIELD, task.getId()), task))
            .orElseThrow(() -> new ResourceNotFoundException.ResourceNotFoundExceptionBuilder()
                    .entityName(Task.class.getSimpleName())
                    .message("Task with id " + task.getId() + " and name " + task.getName() + " not found to update")
                    .build()
            );
  }

  @Override
  public Page<Task> find(TaskQuery taskQuery) {
    LOGGER.info("Retrieving Tasks by Query : {}", taskQuery);
    List<Task> tasks = new ArrayList<>();
    taskMongoCollection.find(taskQuery.toBsonFilter())
        .sort(taskQuery.nameOrderToBson())
        .sort(taskQuery.lastModificationDateOrderToBson())
        .sort(taskQuery.creationDateOrderToBson())
        .skip(taskQuery.getPageIndex() * taskQuery.getPageSize())
        .limit(taskQuery.getPageSize())
        .into(tasks);
    long count = taskMongoCollection.countDocuments(taskQuery.toBsonFilter());
    return new Page<>(
            taskQuery.getPageIndex(),
            taskQuery.getPageSize(),
            count,
            tasks
    );
  }

  @Override
  public DeleteResult deleteById(UUID taskId) {
    LOGGER.info("Deleting Task. Id : {}", taskId.toString());
    return taskMongoCollection.deleteOne(eq(ID_FIELD, taskId.toString()));
  }
}
