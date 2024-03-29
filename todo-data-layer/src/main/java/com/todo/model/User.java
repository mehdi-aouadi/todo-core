package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class User extends Entity {

  private UserProfile userProfile;
  private String password;
  private List<UUID> assignedPrograms;
  private UserHistory userHistory;

  @Builder
  public User(UUID id,
              Instant creationDate,
              Instant lastModificationDate,
              UserProfile userProfile,
              String password,
              List<UUID> assignedPrograms,
              UserHistory userHistory) {
    super(id, creationDate, lastModificationDate);
    this.userProfile = userProfile;
    this.password = password;
    this.assignedPrograms = assignedPrograms;
    this.userHistory = userHistory;
  }
}