package com.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
  private UUID id;
  private Instant creationDate;
  private Instant lastModificationDate;
}
