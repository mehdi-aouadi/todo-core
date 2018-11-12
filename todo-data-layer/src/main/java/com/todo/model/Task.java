package com.todo.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Task {

  private UUID id;

  private String summary;
  private User requester;
  private User assignee;
  private String description;
  private LocalDateTime wishDate;

  public Task(String summary,
              User requester,
              User assignee,
              String description,
              LocalDateTime wishDate) {
    this.summary = summary;
    this.requester = requester;
    this.assignee = assignee;
    this.description = description;
    this.wishDate = wishDate;
  }
}
