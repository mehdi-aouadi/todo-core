package com.todo.contents;

import com.todo.model.Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignedProgramContent {

  private UUID id;
  private LocalDateTime enrollmentDate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Program program;
}
