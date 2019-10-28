package com.todo.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.todo.contents.deserializers.DefaultInstantDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Content {
  private UUID id;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant creationDate;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant lastModificationDate;
}
