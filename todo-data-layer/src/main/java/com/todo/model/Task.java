package com.todo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "Task")
public class Task {

    @Id
    private String id;

    private String summary;
    private User requester;
    private User assignee;
    private String description;
    private LocalDateTime wishDate;

    public Task(String summary, User requester, User assignee, String description, LocalDateTime wishDate) {
        this.summary = summary;
        this.requester = requester;
        this.assignee = assignee;
        this.description = description;
        this.wishDate = wishDate;
    }
}
