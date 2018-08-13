package com.todo.repositories;

import com.todo.model.User;

public interface UserRepository {

    User getUserByEmail(String email);

    void saveUser(User user);

    boolean userExists(String email);

}
