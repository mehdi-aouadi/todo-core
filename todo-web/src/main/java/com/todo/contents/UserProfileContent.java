package com.todo.contents;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserProfileContent {

  private UUID id;
  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String address;

}
