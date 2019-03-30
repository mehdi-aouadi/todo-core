package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.model.Media;
import com.todo.repositories.impl.queries.MediaQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository {

  Media insertMedia(Media media);
  Optional<Media> findMediaById(UUID mediaId);
  Optional<Media> updateMedia(Media media);
  List<Media> findByQuery(MediaQuery mediaQuery);
  DeleteResult deleteMediaById(UUID mediaId);

}
