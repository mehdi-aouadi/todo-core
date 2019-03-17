package com.todo.exceptions;

public class ResourceNotFoundException extends PersistenceException {

  public ResourceNotFoundException(String fieldName, String message) {
    super(fieldName, message);
  }

}
