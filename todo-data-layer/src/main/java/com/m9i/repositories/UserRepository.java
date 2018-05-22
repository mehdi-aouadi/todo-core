package com.m9i.repositories;

import com.m9i.domain.User;

public interface UserRepository {

    User getUser(String email);

    void saveUser(User user);

}
