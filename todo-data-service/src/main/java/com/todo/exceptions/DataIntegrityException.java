package com.todo.exceptions;

public class DataIntegrityException extends PersistenceException {

  public DataIntegrityException(String fieldName, String message) {
    super(fieldName, message);
  }

}
