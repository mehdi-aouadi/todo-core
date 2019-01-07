package com.todo.services;

import com.todo.model.User;

public interface UserService {

  public User findUserByEmail(String email);

  public User saveUser(User user);

  public boolean userExists(String email);

}
