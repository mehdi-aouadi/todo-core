package com.todo.exceptions;

import lombok.*;

@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataIntegrityException extends PersistenceException {

    private String fieldName;
    @Builder
    public DataIntegrityException(String message,
                                  String entityName,
                                  String fieldName) {
        super(message, entityName);
        this.fieldName = fieldName;
    }

}
