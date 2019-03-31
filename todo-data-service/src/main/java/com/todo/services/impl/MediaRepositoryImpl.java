package com.todo.services.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.todo.dbutils.DbManager;
import com.todo.model.Media;
import com.todo.repositories.MediaRepository;
import com.todo.services.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MediaRepositoryImpl implements MediaRepository, ServiceUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(MediaRepositoryImpl.class);

  private static final String ID_FIELD = "_id";
  private static final String LAST_MODIFICATION_DATE_FIELD = "lastModificationDate";


  private final MongoCollection<Media> mediaMongoCollection
      = DbManager.getMongoCollection(Media.class);

  @Override
  public Media insertMedia(Media media) {
    LOGGER.info("Inserting Media : {} ", media.toString());
    mediaMongoCollection.insertOne(media);
    return Media.builder()
        .id(media.getId())
        .creationDate(media.getCreationDate())
        .lastModificationDate(media.getLastModificationDate())
        .mediaName(media.getMediaName())
        .type(media.getType())
        .mediaResourceUrl(media.getMediaResourceUrl())
        .adminUserIdCreatedBy(media.getAdminUserIdCreatedBy())
        .adminUserIdLastModifiedBy(media.getAdminUserIdLastModifiedBy())
        .build();
  }

  @Override
  public Optional<Media> findMediaById(UUID mediaId) {
    LOGGER.info("Retrieving Media with Id : {} ", mediaId.toString());
    return Optional.ofNullable(mediaMongoCollection.find(eq(ID_FIELD, mediaId)).first());
  }

  @Override
  public Optional<Media> updateMedia(Media media) {
    LOGGER.info("Updating Media : {} ", media.toString());
    return Optional.ofNullable(
        mediaMongoCollection.findOneAndReplace(eq(ID_FIELD, media.getId()), media)
    );
  }

  @Override
  public List<Media> findMediasByRange(int skip, int limit) {
    LOGGER.info("Retrieving all Medias. Skip {}, limit {}", skip, limit);
    limit = checkLimit(100, limit, LOGGER);
    return mediaMongoCollection.find()
        .sort(Sorts.ascending(LAST_MODIFICATION_DATE_FIELD))
        .skip(skip)
        .limit(limit)
        .into(new ArrayList<>());
  }

  @Override
  public List<Media> findMediasByName(String mediaName, int skip, int limit) {
    LOGGER.info("Retrieving Medias by name {}, skip {}, limit {}", mediaName, skip, limit);
    limit = checkLimit(100, limit, LOGGER);
    return mediaMongoCollection.find()
        .sort(Sorts.ascending(LAST_MODIFICATION_DATE_FIELD))
        .skip(skip)
        .limit(limit)
        .into(new ArrayList<>());
  }

  @Override
  public DeleteResult deleteMediaById(UUID mediaId) {
    LOGGER.info("Deleting Media with Id : {}", mediaId.toString());
    return mediaMongoCollection.deleteOne(eq(ID_FIELD, mediaId));
  }
}
