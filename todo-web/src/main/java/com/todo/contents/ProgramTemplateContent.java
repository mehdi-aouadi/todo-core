package com.todo.contents;

import lombok.*;

import java.time.Period;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ProgramTemplateContent {

  private UUID id;
  private Period period;
  private String name;
  private String introduction;
  private String description;
  private List<TaskContent> taskContentList;

}
