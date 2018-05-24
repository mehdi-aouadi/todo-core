package com.m9i.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Task")
public class Task {

    @Id
    private String id;

    private String summary;
    private User requester;
    private User assignee;
    private String description;
    private LocalDateTime wishDate;

    public Task () {
    }

    public Task(String summary, User requester, User assignee, String description, LocalDateTime wishDate) {
        this.summary = summary;
        this.requester = requester;
        this.assignee = assignee;
        this.description = description;
        this.wishDate = wishDate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getWishDate() {
        return wishDate;
    }

    public void setWishDate(LocalDateTime wishDate) {
        this.wishDate = wishDate;
    }
}
