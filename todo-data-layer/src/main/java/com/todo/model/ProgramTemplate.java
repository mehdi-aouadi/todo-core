package com.todo.model;

import lombok.*;

import java.time.Period;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ProgramTemplate {

  private UUID id;
  private String name;
  private String description;
  private String introduction;
  private Period period;
  private List<Task> taskList;

}
