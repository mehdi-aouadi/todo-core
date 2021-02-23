package com.todo.exceptions;

import lombok.*;

@ToString(callSuper = true)
public class DataIntegrityException extends PersistenceException {

    @Getter
    private String fieldName;

    public DataIntegrityException(DataIntegrationExceptionBuilder dataIntegrationExceptionBuilder) {
        super(dataIntegrationExceptionBuilder.message, dataIntegrationExceptionBuilder.entityName);
        this.fieldName = dataIntegrationExceptionBuilder.fieldName;
    }

    public static class DataIntegrationExceptionBuilder {

        private String message;
        private String entityName;
        private String fieldName;

        public DataIntegrationExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DataIntegrationExceptionBuilder entityName(String entityName) {
            this.entityName = entityName;
            return this;
        }

        public DataIntegrationExceptionBuilder fieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public DataIntegrityException build() {
            return new DataIntegrityException(this);
        }
    }

}
