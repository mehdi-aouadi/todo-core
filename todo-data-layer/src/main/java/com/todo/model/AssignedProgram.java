package com.todo.model;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class AssignedProgram extends Entity {

  private LocalDateTime enrollmentDate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Program program;

}
