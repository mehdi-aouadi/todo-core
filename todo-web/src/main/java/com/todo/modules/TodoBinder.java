package com.todo.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.todo.services.*;
import com.todo.services.impl.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TodoBinder extends AbstractBinder {

  @Override
  protected void configure() {
    Injector injector = Guice.createInjector(new DataServiceModule());
    bind(injector.getInstance(TaskServiceImpl.class)).to(TaskService.class);
    bind(injector.getInstance(UserServiceImpl.class)).to(UserService.class);
    bind(injector.getInstance(ProgramServiceImpl.class)).to(ProgramService.class);
    bind(injector.getInstance(MediaServiceImpl.class)).to(MediaService.class);
    bind(injector.getInstance(AssignedProgramServiceImpl.class)).to(AssignedProgramService.class);
  }

}
