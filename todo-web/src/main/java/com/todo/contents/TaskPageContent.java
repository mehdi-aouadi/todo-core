package com.todo.contents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskPageContent {
    private int pageIndex;
    private int pageSize;
    private int totalPageCount;
    private int totalCount;
    private List<TaskContent> tasks;
}
