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
public class AssignedTask extends Entity {

    private UUID assignedModuleId;
    private UUID userId;
    private UUID taskId;
    private boolean finished;
    private Instant startDate;
    private Instant endDate;

    @Builder
    public void AssignedTask(
            UUID assignedModuleId,
            UUID taskId,
            boolean finished,
            Instant startDate,
            Instant endDate) {
        this.assignedModuleId = assignedModuleId;
        this.taskId = taskId;
        this.finished = finished;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
