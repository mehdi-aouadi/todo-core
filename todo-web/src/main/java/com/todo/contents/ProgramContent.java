package com.todo.contents;

import lombok.*;

import java.time.Duration;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ProgramContent {

  private UUID id;
  private String title;
  private String description;
  private String introduction;
  private List<UUID> taskList;
  private Duration duration;

}
