package com.todo.exceptions;

import lombok.*;

@ToString(callSuper = true)
public class DataOperationException extends PersistenceException {

    @Getter
    private DataOperation dataOperation;

    public DataOperationException(DataOperationExceptionBuilder dataOperationExceptionBuilder) {
        super(dataOperationExceptionBuilder.message, dataOperationExceptionBuilder.entityName);
        this.dataOperation = dataOperationExceptionBuilder.dataOperation;
    }


    public static class DataOperationExceptionBuilder {

        private String message;
        private String entityName;
        private DataOperation dataOperation;

        public DataOperationExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DataOperationExceptionBuilder entityName(String entityName) {
            this.entityName = entityName;
            return this;
        }

        public DataOperationExceptionBuilder dataOperation(DataOperation dataOperation) {
            this.dataOperation = dataOperation;
            return this;
        }

        public DataOperationException build() {
            return new DataOperationException(this);
        }

    }

}
