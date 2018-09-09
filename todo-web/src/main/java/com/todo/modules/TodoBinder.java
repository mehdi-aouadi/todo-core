package com.todo.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.todo.AllExceptionMapper;
import com.todo.services.TaskService;
import com.todo.services.UserService;
import com.todo.services.impl.TaskServiceImpl;
import com.todo.services.impl.UserServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TodoBinder extends AbstractBinder {

  @Override
  protected void configure() {
    Injector injector = Guice.createInjector(new DataServiceModule());
    bind(injector.getInstance(TaskServiceImpl.class)).to(TaskService.class);
    bind(injector.getInstance(UserServiceImpl.class)).to(UserService.class);
  }

}
