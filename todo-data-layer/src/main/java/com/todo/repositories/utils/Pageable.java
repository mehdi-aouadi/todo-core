package com.todo.repositories.utils;

import com.todo.common.Page;
import com.todo.common.Query;

/**
 * Classes which implement this interface should provide paginated collection of items given a query
 * @param <T> the query type
 * @param <V> the result type
 */
public interface Pageable<T extends Query, V> {

  /**
   * Finds entities based on a query.
   * @param query the query {@link T}
   * @return Paginated collection of {@link V} items
   */
  Page<V> find(T query);

}
