package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.todo.dbutils.MongoDbManager;
import com.todo.model.Program;
import com.todo.repositories.ProgramRepository;
import com.todo.repositories.impl.queries.ProgramQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class ProgramRepositoryMongoImpl implements ProgramRepository {

  private static final String ID_FIELD = "_id";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProgramRepositoryMongoImpl.class);

  private final MongoCollection<Program> programMongoCollection =
      MongoDbManager.getMongoCollection(Program.class);

  @Override
  public Program insertProgram(Program program) {
    LOGGER.info("Inserting new Program : {}", program.toString());
    programMongoCollection.insertOne(program);
    return Program.builder()
        .id(program.getId())
        .creationDate(program.getCreationDate())
        .lastModificationDate(program.getLastModificationDate())
        .name(program.getName())
        .description(program.getDescription())
        .introduction(program.getIntroduction())
        .taskList(program.getTaskList())
        .duration(program.getDuration())
        .build();
  }

  @Override
  public Program updateProgram(Program program) {
    LOGGER.info("Inserting new Program : {}", program.toString());
    programMongoCollection.findOneAndReplace(eq(ID_FIELD, program.getId()), program);
    return Program.builder()
        .id(program.getId())
        .creationDate(program.getCreationDate())
        .lastModificationDate(program.getLastModificationDate())
        .name(program.getName())
        .description(program.getDescription())
        .introduction(program.getIntroduction())
        .taskList(program.getTaskList())
        .duration(program.getDuration())
        .build();
  }

  @Override
  public Program findProgramById(UUID programId) {
    LOGGER.info("Retrieving Program. Id : {}", programId);
    return programMongoCollection.find(eq(ID_FIELD, programId.toString())).first();
  }

  @Override
  public List<Program> findProgramsByQuery(ProgramQuery programQuery) {
    LOGGER.info("Retrieving Programs by Query : {}", programQuery);

    return programMongoCollection.find(programQuery.toBsonFilter())
        .sort(programQuery.nameOrderToBson())
        .sort(programQuery.lastModificationDateOrderToBson())
        .sort(programQuery.creationDateOrderToBson())
        .skip(programQuery.getPageIndex())
        .limit(programQuery.getPageSize())
        .into(new ArrayList<>());
  }

  @Override
  public DeleteResult deleteProgramById(UUID programId) {
    LOGGER.info("Deletin Program. Id : {}", programId);
    return programMongoCollection.deleteOne(eq(ID_FIELD, programId.toString()));
  }
}
