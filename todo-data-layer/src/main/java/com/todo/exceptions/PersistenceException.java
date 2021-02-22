package com.todo.exceptions;

import lombok.*;

@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersistenceException extends IllegalArgumentException {

    String entityName;

    @Builder
    public PersistenceException(String message,
                                 String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
