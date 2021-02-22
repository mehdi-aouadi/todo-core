package com.todo.exceptions;

import lombok.*;

@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataOperationException extends PersistenceException {

    private DataOperation dataOperation;

    @Builder
    public DataOperationException(String message,
                                  String entityName,
                                  DataOperation dataOperation) {
        super(message, entityName);
        this.dataOperation = dataOperation;
    }

}
