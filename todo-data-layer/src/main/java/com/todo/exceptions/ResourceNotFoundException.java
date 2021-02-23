package com.todo.exceptions;

import lombok.ToString;

@ToString(callSuper = true)
public class ResourceNotFoundException extends PersistenceException {

    public ResourceNotFoundException(ResourceNotFoundExceptionBuilder resourceNotFoundExceptionBuilder) {
        super(resourceNotFoundExceptionBuilder.message, resourceNotFoundExceptionBuilder.entityName);
    }

    public static class ResourceNotFoundExceptionBuilder {

        private String message;
        private String entityName;

        public ResourceNotFoundExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResourceNotFoundExceptionBuilder entityName(String entityName) {
            this.entityName = entityName;
            return this;
        }

        public ResourceNotFoundException build() {
            return new ResourceNotFoundException(this);
        }

    }
}
