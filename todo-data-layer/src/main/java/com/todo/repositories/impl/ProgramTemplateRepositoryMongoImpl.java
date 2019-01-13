package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Sorts.*;
import com.todo.dbutils.DbManager;
import com.todo.model.ProgramTemplate;
import com.todo.repositories.ProgramTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class ProgramTemplateRepositoryMongoImpl implements ProgramTemplateRepository {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProgramTemplateRepositoryMongoImpl.class);

  private final MongoCollection<ProgramTemplate> mongoCollection =
    DbManager.getMongoCollection(ProgramTemplate.class);

  @Override
  public ProgramTemplate saveProgramTemplate(ProgramTemplate programTemplate) {
    LOGGER.info("Inserting new ProgramTemplate. Id : {}, Name : {}", programTemplate.getId(),
        programTemplate.getName());
    mongoCollection.insertOne(programTemplate);
    return new ProgramTemplate(programTemplate.getId(), programTemplate.getName(), programTemplate.getDescription(),
            programTemplate.getIntroduction(), programTemplate.getPeriod(),  programTemplate.getTaskList());
  }

  @Override
  public ProgramTemplate findProgramTemplateById(UUID programTemplateId) {
    LOGGER.info("Retrieving ProgramTemplate. Id : {}", programTemplateId);
    return mongoCollection.find(eq("id", programTemplateId)).first();
  }

  @Override
  public ProgramTemplate findProgramTemplateByName(String templateName) {
    LOGGER.info("Retrieving ProgramTemplate. Name : {}", templateName);
    return mongoCollection.find(eq("name", templateName)).first();
  }

  @Override
  public List<ProgramTemplate> findProgramTemplatesByRange(int skip, int limit) {
    LOGGER.info("Retrieving ProgramTemplates. From : {}, To : {}", skip, limit);

    skip = skip > 100 ? 100:skip;

    return mongoCollection.find()
        .sort(Sorts.ascending("name"))
        .skip(skip)
        .limit(limit)
        .into(new ArrayList<>());
  }
}
