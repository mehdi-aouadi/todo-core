package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class User extends Entity {

  private UserProfile userProfile;
  private char[] password;
  private List<AssignedProgram> assignedPrograms;
  private UserHistory userHistory;

  @Builder
  public User(UUID id,
              LocalDateTime creationDate,
              LocalDateTime lastModificationDate,
              UserProfile userProfile,
              char[] password,
              List<AssignedProgram> assignedPrograms,
              UserHistory userHistory) {
    super(id, creationDate, lastModificationDate);
    this.userProfile = userProfile;
    this.password = password;
    this.assignedPrograms = assignedPrograms;
    this.userHistory = userHistory;
  }
}