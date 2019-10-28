package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.dbutils.MongoDbManager;
import com.todo.model.Media;
import com.todo.repositories.MediaRepository;
import com.todo.repositories.impl.queries.MediaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MediaRepositoryImpl implements MediaRepository {

  private static final String ID_FIELD = "_id";

  private static final Logger LOGGER = LoggerFactory.getLogger(MediaRepositoryImpl.class);

  private final MongoCollection<Media> mediaMongoCollection
      = MongoDbManager.getMongoCollection(Media.class);

  @Override
  public Media insertMedia(Media media) {
    LOGGER.info("Inserting Media : {} ", media.toString());
    mediaMongoCollection.insertOne(media);
    return Media.builder()
        .id(media.getId())
        .creationDate(media.getCreationDate())
        .lastModificationDate(media.getLastModificationDate())
        .mediaName(media.getName())
        .type(media.getType())
        .mediaResourceUrl(media.getResourceUrl())
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
  public Page<Media> findByQuery(MediaQuery mediaQuery) {
    LOGGER.info("Retrieving Medias by MediaQuery {}", mediaQuery);
    List<Media> medias = new ArrayList<>();
    mediaMongoCollection.find()
        .sort(mediaQuery.nameOrderToBson())
        .sort(mediaQuery.lastModificationDateOrderToBson())
        .sort(mediaQuery.creationDateOrderToBson())
        .skip(mediaQuery.getPageIndex() * mediaQuery.getPageSize())
        .limit(mediaQuery.getPageSize())
        .into(medias);
    long count = mediaMongoCollection.countDocuments(mediaQuery.toBsonFilter());
    return new Page<>(
        mediaQuery.getPageIndex(),
        mediaQuery.getPageSize(),
        count,
        medias
    );
  }

  @Override
  public DeleteResult deleteMediaById(UUID mediaId) {
    LOGGER.info("Deleting Media with Id : {}", mediaId.toString());
    return mediaMongoCollection.deleteOne(eq(ID_FIELD, mediaId));
  }
}
