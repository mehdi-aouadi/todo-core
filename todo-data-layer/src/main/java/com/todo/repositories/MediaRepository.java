package com.todo.repositories;

import com.mongodb.client.result.DeleteResult;
import com.todo.model.Media;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository {

  Media insertMedia(Media media);
  Optional<Media> findMediaById(UUID mediaId);
  Optional<Media> updateMedia(Media media);
  List<Media> findMediasByRange(int skip, int limit);
  List<Media> findMediasByName(String mediaName, int skip, int limit);
  DeleteResult deleteMediaById(UUID mediaId);

}
