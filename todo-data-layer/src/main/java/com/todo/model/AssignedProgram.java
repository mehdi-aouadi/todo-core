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
public class AssignedProgram extends Entity {

  private UUID userId;
  private Instant enrollmentDate;
  private Instant startDate;
  private Instant endDate;
  private UUID programId;
  private List<AssignedModule> assignedModules;

  @Builder
  public AssignedProgram(UUID id,
                         Instant creationDate,
                         Instant lastModificationDate,
                         UUID userId,
                         Instant enrollmentDate,
                         Instant startDate,
                         Instant endDate,
                         UUID programId,
                         List<AssignedModule> assignedModules) {
    super(id, creationDate, lastModificationDate);
    this.userId = userId;
    this.enrollmentDate = enrollmentDate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.programId = programId;
    this.assignedModules = assignedModules;
  }

}
