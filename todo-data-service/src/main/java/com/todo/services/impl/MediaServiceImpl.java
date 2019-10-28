package com.todo.services.impl;

import com.todo.common.Page;
import com.todo.exceptions.DataIntegrityException;
import com.todo.model.Media;
import com.todo.repositories.MediaRepository;
import com.todo.repositories.impl.queries.MediaQuery;
import com.todo.services.MediaService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

  private MediaRepository mediaRepository;

  @Inject
  public MediaServiceImpl(MediaRepository mediaRepository) {
    this.mediaRepository = mediaRepository;
  }


  @Override
  public Media createMedia(Media media) {
    if(media.getId() != null) {
      throw new DataIntegrityException("mediaId", "To create a new Media mediaId must be null");
    } else {
      checkMedia(media);
      media.setId(UUID.randomUUID());
      media.setCreationDate(LocalDateTime.now());
      media.setLastModificationDate(LocalDateTime.now());
      return this.mediaRepository.insertMedia(media);
    }
  }

  @Override
  public Optional<Media> updteMedia(Media media) {
    if(media.getId() == null) {
      throw new DataIntegrityException("mediaId", "To update a Media mediaId is mandatory");
    } else {
      checkMedia(media);
      media.setLastModificationDate(LocalDateTime.now());
      return this.mediaRepository.updateMedia(media);
    }
  }

  @Override
  public Optional<Media> findMediaById(UUID mediaId) {
    return this.mediaRepository.findMediaById(mediaId);
  }

  @Override
  public Page<Media> findMediasByQuery(MediaQuery mediaQuery) {
    return mediaRepository.findByQuery(mediaQuery);
  }

  @Override
  public void deleteMediaById(UUID mediaId) {
    mediaRepository.deleteMediaById(mediaId);
  }

  private void checkMedia(Media media) {
    if(StringUtils.isBlank(media.getName())) {
      throw new DataIntegrityException("Media Name", "Media Name is manadatory");
    }
  }
}
