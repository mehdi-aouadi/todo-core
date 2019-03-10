package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserHistory extends Entity {

  private Double score;
  private List<AssignedProgram> finishedAssignedPrograms;
  private List<AssignedProgram> cancelledAssignedPrograms;

  @Builder
  public UserHistory(UUID id,
                     LocalDateTime creationDate,
                     LocalDateTime lastModificationDate,
                     Double score,
                     List<AssignedProgram> finishedAssignedPrograms,
                     List<AssignedProgram> cancelledAssignedPrograms) {
    super(id, creationDate, lastModificationDate);
    this.score = score;
    this.finishedAssignedPrograms = finishedAssignedPrograms;
    this.cancelledAssignedPrograms = cancelledAssignedPrograms;

  }

}
