package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.dbutils.MongoDbManager;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.model.Program;
import com.todo.repositories.ProgramRepository;
import com.todo.repositories.impl.queries.ProgramQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class ProgramRepositoryMongoImpl implements ProgramRepository {

  private static final String PROGRAM_ID_FIELD_NAME = "_id";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProgramRepositoryMongoImpl.class);

  private final MongoCollection<Program> programMongoCollection =
      MongoDbManager.getMongoCollection(Program.class);

  @Override
  public Program insert(Program program) {
    LOGGER.info("Inserting new Program : {}", program.toString());
    try {
      programMongoCollection.insertOne(program);
      return program;
    } catch (Exception e) {
      LOGGER.error("Insert operation of Program {} failed due to internal error. Stack Trace : {}",
              program.getName(),
              e.getStackTrace().toString());
      return null;
    }
  }

  @Override
  public Program update(Program program) throws ResourceNotFoundException {
    LOGGER.info("Inserting new Program : {}", program.toString());
    return Optional.ofNullable(programMongoCollection.findOneAndReplace(eq(PROGRAM_ID_FIELD_NAME, program.getId()), program))
            .orElseThrow(() -> ResourceNotFoundException.builder()
            .entityName(Program.class.getSimpleName())
            .message("The program with id " + program.getId() + " and name " + program.getName() + " to update is not found")
            .build());
  }

  @Override
  public Optional<Program> findById(UUID programId) {
    return Optional.ofNullable(programMongoCollection.find(eq(PROGRAM_ID_FIELD_NAME, programId.toString())).first());
  }

  @Override
  public DeleteResult deleteById(UUID programId) {
    LOGGER.info("Deleting Program. Id : {}", programId);
    return programMongoCollection.deleteOne(eq(PROGRAM_ID_FIELD_NAME, programId.toString()));
  }

  @Override
  public Page<Program> find(ProgramQuery query) {
    LOGGER.info("Retrieving Programs by Query {}", query);
    List<Program> programs = new ArrayList<>();

    programMongoCollection.find(query.toBsonFilter())
            .skip(query.getPageIndex())
            .limit(query.getPageSize())
            .into(programs);
    long count = programMongoCollection.countDocuments(query.toBsonFilter());
    return new Page<> (query.getPageIndex(), query.getPageSize(), count, programs);
  }
}
