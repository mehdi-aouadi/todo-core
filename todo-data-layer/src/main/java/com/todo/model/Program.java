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
public class Program extends Entity {

  private String name;
  private String description;
  private String introduction;
  private List<Module> modules;
  private Duration duration;
  private ProgramSubscription programSubscription;
  private Double price;

  @Builder
  public Program(UUID id,
                 Instant creationDate,
                 Instant lastModificationDate,
                 String name,
                 String description,
                 String introduction,
                 List<Module> modules,
                 Duration duration,
                 ProgramSubscription programSubscription,
                 Double price) {
    super(id, creationDate, lastModificationDate);
    this.name = name;
    this.description = description;
    this.introduction = introduction;
    this.modules = modules;
    this.duration = duration;
    this.programSubscription = programSubscription;
    this.price = price;
  }
}
