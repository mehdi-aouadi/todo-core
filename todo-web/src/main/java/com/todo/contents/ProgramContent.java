package com.todo.contents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramContent {

  private UUID id;
  private String title;
  private String description;
  private String introduction;
  private List<UUID> taskList;
  private Duration duration;

}
