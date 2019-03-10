package com.todo.repositories.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.todo.dbutils.DbManager;
import com.todo.model.Program;
import com.todo.repositories.ProgramRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgramRepositoryMongoImpl implements ProgramRepository {

  private static final Logger LOGGER =
          LoggerFactory.getLogger(ProgramRepositoryMongoImpl.class);

  private static final ReplaceOptions REPLACE_OPTIONS
      = ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true));

  private final MongoCollection<Program> programMongoCollection =
          DbManager.getMongoCollection(Program.class);

  @Override
  public Program saveProgram(Program program) {
    LOGGER.info("Inserting new Program : {}", program.toString());
    programMongoCollection.replaceOne(eq("id", program.getId().toString()), program, REPLACE_OPTIONS);
    return Program.builder()
            .id(program.getId())
            .creationDate(program.getCreationDate())
            .lastModificationDate(program.getLastModificationDate())
            .title(program.getTitle())
            .description(program.getDescription())
            .introduction(program.getIntroduction())
            .taskList(program.getTaskList())
            .duration(program.getDuration())
            .build();
  }

  @Override
  public Program findProgramById(UUID programId) {
    LOGGER.info("Retrieving Program. Id : {}", programId);
    return programMongoCollection.find(eq("id", programId.toString())).first();
  }

  @Override
  public List<Program> findProgramsByTitle(String programName, int skip, int limit) {
    LOGGER.info("Retrieving Programs by name : {}", programName);

    return programMongoCollection.find(regex("title", programName))
            .sort(Sorts.ascending("title"))
            .skip(skip)
            .limit(limit)
            .into(new ArrayList<>());
  }

  @Override
  public List<Program> findProgramsByRange(int skip, int limit) {
    LOGGER.info("Retrieving ProgramTemplates. From : {}, To : {}", skip, limit);
    return programMongoCollection.find()
            .sort(Sorts.ascending("lastModificationDate"))
            .skip(skip)
            .limit(limit)
            .into(new ArrayList<>());
  }

  @Override
  public DeleteResult deleteProgramById(UUID programId) {
    LOGGER.info("Deletin Program. Id : {}", programId);
    return programMongoCollection.deleteOne(eq("id", programId.toString()));
  }
}
