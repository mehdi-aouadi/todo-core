package com.todo.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
    private UUID id;
    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;
}
