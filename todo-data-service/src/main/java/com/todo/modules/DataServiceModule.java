package com.todo.modules;

import com.google.inject.AbstractModule;
import com.todo.repositories.*;
import com.todo.repositories.impl.*;

public class DataServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(TaskRepository.class).to(TaskRepositoryMongoImpl.class);
    bind(UserRepository.class).to(UserRepositoryMongoImpl.class);
    bind(ProgramRepository.class).to(ProgramRepositoryMongoImpl.class);
    bind(MediaRepository.class).to(MediaRepositoryImpl.class);
    bind(AssignedProgramRepository.class).to(AssignedProgramRepositoryMongoImpl.class);
  }

}
