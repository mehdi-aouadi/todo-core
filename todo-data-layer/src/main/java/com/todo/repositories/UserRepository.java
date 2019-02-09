package com.todo.repositories;

import com.todo.model.User;

public interface UserRepository {

  User findUserByEmail(String email);

  User saveUser(User user);

  boolean userExists(String email);

}
