package com.todo.services;

import com.todo.common.Page;
import com.todo.model.Media;
import com.todo.repositories.impl.queries.MediaQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaService {

  Media createMedia(Media media);

  Media updateMedia(Media media);

  Optional<Media> findMediaById(UUID mediaId);

  Page<Media> findMediasByQuery(MediaQuery mediaQuery);

  void deleteMediaById(UUID mediaId);

}
