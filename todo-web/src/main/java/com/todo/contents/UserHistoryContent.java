package com.todo.contents;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserHistoryContent extends Content {

  private Double score;
  private List<AssignedProgramContent> finishedAssignedPrograms;
  private List<AssignedProgramContent> cancelledAssignedPrograms;

}
