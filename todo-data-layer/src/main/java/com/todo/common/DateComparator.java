package com.todo.common;

import lombok.Value;

import java.time.Instant;


/**
 * A comparator (greater or lower than) and a date for {@link Query}.
 */
@Value
public class DateComparator {

  private final Operator operator;
  private final Instant instant;

  public enum Operator {
    GREATER_THAN,
    LOWER_THAN,
    ;
  }
}
