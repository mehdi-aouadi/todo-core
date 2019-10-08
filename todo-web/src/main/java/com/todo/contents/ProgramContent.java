package com.todo.contents;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramContent extends Content{

  private String name;
  private String description;
  private String introduction;
  private List<UUID> taskList;
  @JsonSerialize(using = DurationSerializer.class)
  @JsonDeserialize(using = DurationDeserializer.class)
  private Duration duration;

}
