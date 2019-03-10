package com.todo.exceptions;

public class DataOperationException extends PersistenceException {

  public DataOperationException(String fieldName, String message) {
    super(fieldName, message);
  }

}
