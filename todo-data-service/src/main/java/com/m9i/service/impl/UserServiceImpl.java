package com.m9i.service.impl;

import com.m9i.model.User;
import com.m9i.repositories.UserRepository;
import com.m9i.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }
}
