package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class UserProfile extends Entity {

  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String address;

  @Builder
  public UserProfile(UUID id,
                     LocalDateTime creationDate,
                     LocalDateTime lastModificationDate,
                     String userName,
                     String firstName,
                     String lastName,
                     String email,
                     String phoneNumber,
                     String address
  ) {
    super(id, creationDate, lastModificationDate);
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

}
