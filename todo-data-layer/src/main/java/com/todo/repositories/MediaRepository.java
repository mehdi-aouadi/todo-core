package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.Media;
import com.todo.repositories.queries.MediaQuery;
import com.todo.repositories.utils.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends Pageable<MediaQuery, Media> {

  /**
   * Inserts a new {@link Media}
   * @param media The {@link Media} to insert
   * @return The inserted {@link Media} or null if the insert operation fails
   */
  Media insertMedia(Media media);

  /**
   * Finds a {@link Media} by its Id
   * @param mediaId The {@link Media} Id
   * @return An {@link Optional<Media>} with the found {@link Media} or with null if not found
   */
  Optional<Media> findMediaById(UUID mediaId);

  /**
   * Updates an existing {@link Media}
   * @param media The {@link Media} to update
   * @return The updated {@link Media}
   * @throws ResourceNotFoundException if the {@link Media} is not found
   */
  Media updateMedia(Media media) throws ResourceNotFoundException;

  /**
   * Deletes an existing {@link Media}
   * @param mediaId The {@link Media} Id to delete
   * @return A delete operation result. See {@link DeleteResult}
   */
  DeleteResult deleteMediaById(UUID mediaId);

}
