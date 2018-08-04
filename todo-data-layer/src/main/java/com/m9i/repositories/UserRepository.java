package com.m9i.repositories;

import com.m9i.model.User;

public interface UserRepository {

    User getUserByEmail(String email);

    void saveUser(User user);

}
