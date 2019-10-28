package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class AssignedProgram extends Entity {

  private Instant enrollmentDate;
  private Instant startDate;
  private Instant endDate;
  private Program program;

  @Builder
  public AssignedProgram(UUID id,
                         Instant creationDate,
                         Instant lastModificationDate,
                         Instant enrollmentDate,
                         Instant startDate,
                         Instant endDate,
                         Program program) {
    super(id, creationDate, lastModificationDate);
    this.enrollmentDate = enrollmentDate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.program = program;
  }

}
