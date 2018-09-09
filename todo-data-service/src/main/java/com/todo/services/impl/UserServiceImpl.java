package com.todo.services.impl;

import com.google.inject.Inject;
import com.todo.exceptions.DataIntegrityException;
import com.todo.model.User;
import com.todo.repositories.UserRepository;
import com.todo.services.UserService;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Inject
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.getUserByEmail(email);
  }

  @Override
  public void saveUser(User user) {
    checkUser(user);
    if (StringUtils.isBlank(user.getId())) {
      user.setId(UUID.randomUUID().toString());
    }
    userRepository.saveUser(user);
  }

  @Override
  public boolean userExists(String email) {
    return userRepository.userExists(email);
  }

  private void checkUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User is null.");
    }
    if (StringUtils.isBlank(user.getEmail())) {
      throw new DataIntegrityException("Missing user email.");
    }
  }
}
