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
  private Period period;
  private String name;
  private String introduction;
  private String description;
  private List<Task> taskList;

}
