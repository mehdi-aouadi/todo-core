package com.todo.repositories.modules;

import com.google.inject.AbstractModule;
import com.todo.repositories.AssignedProgramRepository;
import com.todo.repositories.ProgramRepository;
import com.todo.repositories.impl.AssignedProgramRepositoryMongoImpl;
import com.todo.repositories.impl.ProgramRepositoryMongoImpl;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProgramRepository.class).to(ProgramRepositoryMongoImpl.class);
        bind(AssignedProgramRepository.class).to(AssignedProgramRepositoryMongoImpl.class);
    }

}
