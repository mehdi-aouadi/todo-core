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
public class UserHistory extends Entity {

  private Double score;
  private List<AssignedProgram> finishedAssignedPrograms;
  private List<AssignedProgram> cancelledAssignedPrograms;

  @Builder
  public UserHistory(UUID id,
                     Instant creationDate,
                     Instant lastModificationDate,
                     Double score,
                     List<AssignedProgram> finishedAssignedPrograms,
                     List<AssignedProgram> cancelledAssignedPrograms) {
    super(id, creationDate, lastModificationDate);
    this.score = score;
    this.finishedAssignedPrograms = finishedAssignedPrograms;
    this.cancelledAssignedPrograms = cancelledAssignedPrograms;

  }

}
