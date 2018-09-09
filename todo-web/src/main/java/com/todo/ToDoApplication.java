package com.todo;

import com.todo.modules.TodoBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class ToDoApplication extends ResourceConfig {
  public ToDoApplication() {
    register(new TodoBinder());
    packages(true, "com.todo.controllers");
  }
}
