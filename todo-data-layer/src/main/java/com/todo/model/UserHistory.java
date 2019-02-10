package com.todo.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class UserHistory extends Entity {

    private Double score;
    private List<UUID> finishedAssignedPrograms;
    private List<UUID> cancelledAssignedPrograms;

}
