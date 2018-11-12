package com.todo.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class User {

  private UUID id;

  private String email;
  private String phoneNumber;

  public User(String email, String phoneNumber) {
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
}