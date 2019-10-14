package com.todo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A page of results.
 */
@Getter
@AllArgsConstructor
public class Page<T> {

  private final int pageIndex;
  private final int pageSize;
  private final long totalElementCount;
  private final List<T> content;

  public boolean containsFullContent() {
    return totalElementCount == content.size();
  }

  public long getTotalPageCount() {
    return totalElementCount / pageSize + (totalElementCount % pageSize != 0 ? 1 : 0);
  }
}
