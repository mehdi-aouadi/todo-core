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
public class AssignedModule extends Entity {

    private UUID userId;
    private UUID moduleId;
    private boolean finished;
    private Instant startDate;
    private Instant endDate;

    @Builder
    public AssignedModule(UUID id,
                  Instant creationDate,
                  Instant lastModificationDate,
                  UUID userId,
                  UUID moduleId,
                  boolean finished,
                  Instant startDate,
                  Instant endDate) {
        super(id, creationDate, lastModificationDate);
        this.userId = userId;
        this.moduleId = moduleId;
        this.finished = finished;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
