package com.todo.service.impl;

import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.service.UserService;
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
