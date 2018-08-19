package com.todo;

import com.todo.modules.DataServiceModule;
import com.todo.modules.TodoBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class ToDoApplication extends ResourceConfig {
    public ToDoApplication() {
        //new ResourceConfig().register(new TodoBinder());
        register(new TodoBinder());
        packages(true,"com.todo.controllers");
    }
}
