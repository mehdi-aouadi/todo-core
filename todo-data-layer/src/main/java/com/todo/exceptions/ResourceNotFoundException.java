package com.todo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceNotFoundException extends PersistenceException {

    public ResourceNotFoundException(String message,
                                     String entityName) {
        super(message, entityName);
    }

}
