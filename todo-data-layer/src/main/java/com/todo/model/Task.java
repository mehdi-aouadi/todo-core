package com.todo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Task {

  private String id;

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
