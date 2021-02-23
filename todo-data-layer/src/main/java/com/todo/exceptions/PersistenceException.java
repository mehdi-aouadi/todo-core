package com.todo.exceptions;

import lombok.*;

@ToString(callSuper = true)
public class PersistenceException extends IllegalArgumentException {

    @Getter
    private String entityName;

    public PersistenceException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public PersistenceException(PersistenceExceptionBuilder persistenceExceptionBuilder) {
        super(persistenceExceptionBuilder.message);
        this.entityName = persistenceExceptionBuilder.entityName;
    }

    public static class PersistenceExceptionBuilder {

        private String entityName;
        private String message;

        public PersistenceExceptionBuilder entityName(String entityName) {
            this.entityName = entityName;
            return this;
        }

        public PersistenceExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public PersistenceException build() {
            return new PersistenceException(this);
        }
    }
}
