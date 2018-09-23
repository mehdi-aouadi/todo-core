package com.todo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistenceException extends IllegalArgumentException {

  private String fieldName;

  public PersistenceException(String fieldName, String message) {
    super(message);
    this.fieldName = fieldName;
  }

}
