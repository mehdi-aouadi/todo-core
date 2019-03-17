package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class AssignedProgram extends Entity {

  private LocalDateTime enrollmentDate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Program program;

  @Builder
  public AssignedProgram(UUID id,
                         LocalDateTime creationDate,
                         LocalDateTime lastModificationDate,
                         LocalDateTime enrollmentDate,
                         LocalDateTime startDate,
                         LocalDateTime endDate,
                         Program program) {
    super(id, creationDate, lastModificationDate);
    this.enrollmentDate = enrollmentDate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.program = program;
  }

}
