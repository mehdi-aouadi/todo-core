package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.Task;
import com.todo.repositories.impl.queries.TaskQuery;
import com.todo.repositories.utils.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends Pageable<TaskQuery, Task> {

  /**
   * Inserts a new {@link Task}.
   * @param task The {@link Task} to insert
   * @return The inserted {@link Task} or null if the insert operation fails
   */
  Task insert(Task task);

  /**
   * Updates an existing {@link Task}.
   * @param task the {@link Task} to update
   * @return The updated {@link Task} if found
   * @throws ResourceNotFoundException if the {@link Task} is not found
   */
  Task update(Task task) throws ResourceNotFoundException;

  /**
   * Finds a {@link Task} by its id.
   * @param taskId The {@link Task} Id
   * @return The {@link Task} if found, null otherwise
   */
  Optional<Task> findById(UUID taskId);

  /**
   * Deletes a {@link Task} by its Id.
   * @param taskId The {@link Task} Id
   * @return The delete operation result. See {@link DeleteResult}
   */
  DeleteResult deleteById(UUID taskId);

}
