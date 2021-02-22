package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Duration {

    private int days;
    private int weeks;
    private int months;

    @Builder
    public Duration (int days, int weeks, int months) {
        this.days = days;
        this.weeks = weeks;
        this.months = months;
    }

}
