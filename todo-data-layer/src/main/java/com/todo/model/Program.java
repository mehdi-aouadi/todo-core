package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Program extends Entity {

  private String name;
  private String description;
  private String introduction;
  private List<UUID> taskList;
  private Duration duration;

  @Builder
  public Program(UUID id,
                 LocalDateTime creationDate,
                 LocalDateTime lastModificationDate,
                 String name,
                 String description,
                 String introduction,
                 List<UUID> taskList,
                 Duration duration) {
    super(id, creationDate, lastModificationDate);
    this.name = name;
    this.description = description;
    this.introduction = introduction;
    this.taskList = taskList;
    this.duration = duration;
  }
}
