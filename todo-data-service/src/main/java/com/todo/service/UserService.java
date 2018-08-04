package com.todo.service;

import com.todo.model.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);

}
