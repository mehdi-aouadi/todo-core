package com.todo.contents;

import com.todo.model.AssignedProgram;
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
public class UserContent {

  private UUID id;
  private UserProfileContent userProfile;
  private List<AssignedProgram> assignedPrograms;
  private UserHistoryContent userHistory;

}
