package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class AdminUser extends Entity {

  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private char[] password;

  @Builder
  public AdminUser(UUID id,
                   Instant creationDate,
                   Instant lastModificationDate,
                   String userName,
                   String firstName,
                   String lastName,
                   String email,
                   char[] password) {
    super(id, creationDate, lastModificationDate);
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

}
