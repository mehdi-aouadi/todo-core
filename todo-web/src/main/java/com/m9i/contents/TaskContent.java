package com.m9i.contents;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class TaskContent {

    private String id;
    private String summary;
    private String requesterId;
    private String requesterEmail;
    private String assigneeId;
    private String assigneeEmail;
    private String description;
    private LocalDateTime wishDate;

}
