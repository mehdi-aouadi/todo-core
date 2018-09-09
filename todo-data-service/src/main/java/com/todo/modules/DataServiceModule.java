package com.todo.modules;

import com.google.inject.AbstractModule;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;
import com.todo.repositories.impl.TaskRepositoryMongoImpl;
import com.todo.repositories.impl.UserRepositoryMongoImpl;

public class DataServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(TaskRepository.class).to(TaskRepositoryMongoImpl.class);
    bind(UserRepository.class).to(UserRepositoryMongoImpl.class);
  }

}
