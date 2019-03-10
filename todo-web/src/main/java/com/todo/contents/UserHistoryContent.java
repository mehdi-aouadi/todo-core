package com.todo.contents;

import com.todo.model.AssignedProgram;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserHistoryContent {

  private Double score;
  private List<AssignedProgramContent> finishedAssignedPrograms;
  private List<AssignedProgramContent> cancelledAssignedPrograms;

}
