package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Module extends Entity {

    private String name;
    private String description;
    private String introduction;

    @Builder
    public Module(UUID id,
                  Instant creationDate,
                  Instant lastModificationDate,
                  String name,
                  String description,
                  String introduction) {
        super(id, creationDate, lastModificationDate);
        this.name = name;
        this.description = description;
        this.introduction = introduction;
    }
}
