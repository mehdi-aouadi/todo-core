package com.todo.service.impl;

import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.service.UserService;
import exceptions.DataIntegrityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        checkUser(user);
        if(StringUtils.isBlank(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        userRepository.saveUser(user);
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.userExists(email);
    }

    private void checkUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        if(StringUtils.isBlank(user.getEmail())) {
            throw new DataIntegrityException("Missing user email.");
        }
    }
}
