package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Task extends Entity {

  private UUID programId;
  private UUID moduleId;
  private String name;
  private String summary;
  private String description;
  private List<Media> medias;

  @Builder
  public Task(UUID id,
              Instant creationDate,
              Instant lastModificationDate,
              UUID programId,
              UUID moduleId,
              String name,
              String summary,
              String description,
              List<Media> medias) {
    super(id, creationDate, lastModificationDate);
    this.programId = programId;
    this.moduleId = moduleId;
    this.name = name;
    this.summary = summary;
    this.description = description;
    this.medias = medias;
  }
}
