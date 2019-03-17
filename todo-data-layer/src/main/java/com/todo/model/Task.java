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
public class Task extends Entity {

  private String name;
  private String summary;
  private String description;
  private Duration duration;
  private List<UUID> mediaIds;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @Builder
  public Task(UUID id,
              LocalDateTime creationDate,
              LocalDateTime lastModificationDate,
              String name,
              String summary,
              String description,
              Duration duration,
              List<UUID> mediaIds,
              LocalDateTime startDate,
              LocalDateTime endDate) {
    super(id, creationDate, lastModificationDate);
    this.name = name;
    this.summary = summary;
    this.description = description;
    this.duration = duration;
    this.mediaIds = mediaIds;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
