package com.todo.contents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramContent extends Content{

  private String name;
  private String description;
  private String introduction;
  private List<UUID> modules;
  private DurationContent duration;

}
