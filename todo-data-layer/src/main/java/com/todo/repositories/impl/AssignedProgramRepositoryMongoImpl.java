package com.todo.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.todo.common.Page;
import com.todo.dbutils.MongoDbManager;
import com.todo.model.AssignedProgram;
import com.todo.repositories.AssignedProgramRepository;
import com.todo.repositories.impl.queries.AssignedProgramQuery;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class AssignedProgramRepositoryMongoImpl implements AssignedProgramRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignedProgramRepositoryMongoImpl.class);

    private static final String ASSIGNED_PROGRAM_ID_FIELD = "_id";

    private final MongoCollection<AssignedProgram> assignedProgramMongoCollection
            = MongoDbManager.getMongoCollection(AssignedProgram.class);

    @Override
    public AssignedProgram insert(AssignedProgram assignedProgram) {
        try {
            assignedProgramMongoCollection.insertOne(assignedProgram);
            return assignedProgram;
        } catch (Exception e) {
            LOGGER.error("Insert operation of AssignedProgram with Id {} failed due to internal error. Stack Trace : {}",
                    assignedProgram.getId(),
                    e.getStackTrace().toString());
            return null;
        }
    }

    @Override
    public Optional<AssignedProgram> findById(UUID assignedProgramId) {
        return Optional.ofNullable(
                assignedProgramMongoCollection.find(
                        eq(ASSIGNED_PROGRAM_ID_FIELD, assignedProgramId.toString())
                ).first()
        );
    }

    @Override
    public Page<AssignedProgram> find(AssignedProgramQuery assignedProgramQuery) {
        LOGGER.info("Retrieving Assigned Programs by Query {}", assignedProgramQuery.toString());
        List<AssignedProgram> assignedPrograms = new ArrayList<>();
        assignedProgramMongoCollection
                .find(assignedProgramQuery.toBsonFilter())
                .skip(assignedProgramQuery.getPageIndex())
                .limit(assignedProgramQuery.getPageSize())
                .into(assignedPrograms);
        long count = assignedProgramMongoCollection.countDocuments(assignedProgramQuery.toBsonFilter());
        return new Page<>(
                assignedProgramQuery.getPageIndex(),
                assignedProgramQuery.getPageSize(),
                count,
                assignedPrograms
        );
    }

    @Override
    public DeleteResult deleteById(UUID assignedProgramId) {
        LOGGER.info("Deleting Assigned Program with Id {}",
                assignedProgramId.toString());
        return assignedProgramMongoCollection.deleteOne(
                eq(ASSIGNED_PROGRAM_ID_FIELD, assignedProgramId.toString()));
    }
}
