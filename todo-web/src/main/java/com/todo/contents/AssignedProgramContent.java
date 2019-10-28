package com.todo.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.todo.contents.deserializers.DefaultInstantDeserializer;
import com.todo.model.Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignedProgramContent extends Content {

  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant enrollmentDate;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant startDate;
  @JsonSerialize(using = InstantSerializer.class)
  @JsonDeserialize(using = DefaultInstantDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant endDate;
  private Program program;
}
