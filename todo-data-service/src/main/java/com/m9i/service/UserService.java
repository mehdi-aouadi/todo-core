package com.m9i.service;

import com.m9i.model.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);

}
