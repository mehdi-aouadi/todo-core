package com.todo.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.todo.contents.deserializers.DefaultInstantDeserializer;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class TaskContent extends Content {

  private String name;
  private String summary;
  private String description;
  @JsonSerialize(using = DurationSerializer.class)
  @JsonDeserialize(using = DurationDeserializer.class)
  private Duration duration;
  private List<UUID> mediaIds;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant startDate;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant endDate;

}
