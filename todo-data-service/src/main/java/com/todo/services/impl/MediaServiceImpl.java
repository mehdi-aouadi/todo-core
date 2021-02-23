package com.todo.services.impl;

import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.exceptions.DataIntegrityException;
import com.todo.exceptions.DataOperation;
import com.todo.exceptions.DataOperationException;
import com.todo.model.Media;
import com.todo.repositories.MediaRepository;
import com.todo.repositories.queries.MediaQuery;
import com.todo.services.MediaService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

  private static String MEDIA_ENTITY_NAME = Media.class.getSimpleName();
  private static String MEDIA_ID_FIELD_NAME = "mediaId";
  private static String MEDIA_NAME_FIELD_NAME = "mediaName";

  private MediaRepository mediaRepository;

  @Inject
  public MediaServiceImpl(MediaRepository mediaRepository) {
    this.mediaRepository = mediaRepository;
  }


  @Override
  public Media createMedia(Media media) {
    checkMediaForCreation(media);
    media.setId(UUID.randomUUID());
    media.setCreationDate(Instant.now());
    media.setLastModificationDate(Instant.now());
    return this.mediaRepository.insertMedia(media);
  }

  @Override
  public Media updateMedia(Media media) {
    checkMediaForUpdate(media);
    media.setLastModificationDate(Instant.now());
    return this.mediaRepository.updateMedia(media);
  }

  @Override
  public Optional<Media> findMediaById(UUID mediaId) {
    return this.mediaRepository.findMediaById(mediaId);
  }

  @Override
  public Page<Media> findMediasByQuery(MediaQuery mediaQuery) {
    return mediaRepository.find(mediaQuery);
  }

  @Override
  public void deleteMediaById(UUID mediaId) {
    DeleteResult deleteResult = mediaRepository.deleteMediaById(mediaId);
    if(!deleteResult.wasAcknowledged() || deleteResult.getDeletedCount() == 0) {
      throw new DataOperationException.DataOperationExceptionBuilder()
              .entityName(MEDIA_ENTITY_NAME)
              .dataOperation(DataOperation.DELETE)
              .message("Unable to delete Media with id " + mediaId.toString())
              .build();
    }
  }

  private void checkMediaForCreation(Media media) {
    if(media.getId() != null) {
      throw new DataIntegrityException.DataIntegrationExceptionBuilder()
              .entityName(Media.class.getSimpleName())
              .fieldName(MEDIA_ID_FIELD_NAME)
              .message("To insert a new Media, Id must be null. Media : " + media)
              .build();
    }
    if(StringUtils.isBlank(media.getName())) {
      throw new DataIntegrityException.DataIntegrationExceptionBuilder()
              .entityName(Media.class.getSimpleName())
              .fieldName(MEDIA_NAME_FIELD_NAME)
              .message("Cannot insert Media. Missing Media NAme. Media : " + media)
              .build();
    }
  }

  private void checkMediaForUpdate(Media media) {
    if(media.getId() == null) {
      throw new DataIntegrityException.DataIntegrationExceptionBuilder()
              .entityName(Media.class.getSimpleName())
              .fieldName(MEDIA_ID_FIELD_NAME)
              .message("Cannot update a Media with a null Id. Media : " + media)
              .build();
    }
    if(StringUtils.isBlank(media.getName())) {
      throw new DataIntegrityException.DataIntegrationExceptionBuilder()
              .entityName(Media.class.getSimpleName())
              .fieldName(MEDIA_NAME_FIELD_NAME)
              .message("Media NAme is mandatory. Cannot update a Media with a null name. Media : " + media)
              .build();
    }
  }
}
