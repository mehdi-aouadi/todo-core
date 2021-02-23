package com.todo.queries;

import com.todo.common.DateComparator;
import com.todo.common.Order;
import lombok.Getter;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.Instant.ofEpochMilli;
import static java.util.stream.Collectors.joining;

public abstract class Query<DQ extends com.todo.common.Query> {

  public static final String RANGE = "range";
  public static final int MAX_RANGE_LENGTH = 200;

  private final MultivaluedMap<String, String> queryParameters;
  protected DQ query;
  protected Boolean valid;
  protected StringBuilder paramErrorsBuilder;

  /**
   * Build a {@link Query} from HTTP query parameters.
   *
   * @param queryParameters HTTP query parameters
   */
  protected Query(MultivaluedMap<String, String> queryParameters) {
    this.queryParameters = queryParameters;
    this.valid = null;
    this.paramErrorsBuilder = new StringBuilder();
    this.query = null;
  }

  public boolean isValid() {
    if (valid == null) {
      validate();
    }
    return valid;
  }

  protected void validate() {
    Page page = new Page(param(RANGE).orElse(null));
    if (!page.valid) {
      paramErrorsBuilder.append(
          String.format("Parameter `%s` is invalid. Must be of the form x-y. With x the 0-based"
                  + " index of the first element and y the one of the last (included). x and y"
                  + " must respect : x %% (y - x + 1) == 0 (i.e. The range must be a page with x"
                  + " first of this page). The range length can not exceed %s.\n",
              RANGE,
              MAX_RANGE_LENGTH));
    }
    this.valid = page.valid;
  }

  /**
   * Build a {@link com.todo.common.Query} based on parameters.
   * <p>
   * Note that calling this method before {@link Query#isValid()} or if it
   * returns false lead to IllegalStateException..
   *
   * @return Query
   * @throws IllegalStateException If {@link Query#isValid()} hasn't been
   *                               called or it returns false.
   */
  public DQ toDomainQuery()
      throws IllegalStateException {
    if (valid == null) {
      throw new IllegalStateException("Check validity of parameters first by calling isValid()");
    }
    if (!isValid()) {
      throw new IllegalStateException("Invalid set of parameters can not lead to valid query : "
          + this.queryParameters);
    }
    if (query == null) {
      try {
        query = buildQuery();
      } catch (IllegalArgumentException e) {
        throw new IllegalStateException("Invalid implementation. Initialising builder should not be"
            + " done with invalid set of parameters.");
      }
    }
    return query;
  }

  protected abstract DQ buildQuery();


  /**
   * @return Descriptive error message
   * @throws IllegalStateException When {@link Query#isValid()} has not been
   *                               called before and returns true.
   */
  public String errorMessage() throws IllegalStateException {
    if (valid == null && isValid()) {
      throw new IllegalStateException(
          "Query parameters were valid. Calling errorMessage is likely to be a misuse. Use return"
              + " of isValid()");
    }
    return paramErrorsBuilder.toString();
  }

  protected Boolean paramBoolean(String paramName) {
    return param(paramName).map(Boolean::valueOf).orElse(null);
  }

  protected DateComparator paramDateComparator(String paramName, DateComparator.Operator operator) {
    return param(paramName)
        .map(param -> new DateComparator(operator, ofEpochMilli(Long.valueOf(param))))
        .orElse(null);
  }

  protected Order paramOrder(String order) {
    return param(order).map(Order::valueOf).orElse(null);
  }

  public Optional<String> param(String paramName) {
    List<String> paramValues = queryParameters.get(paramName);
    if (paramValues == null || paramValues.size() < 1) {
      return Optional.empty();
    }
    return Optional.ofNullable(paramValues.get(0));
  }

  protected Optional<List<String>> paramList(String paramName) {
    List<String> paramValues = queryParameters.get(paramName);
    if (paramValues == null || paramValues.size() < 1) {
      return Optional.empty();
    }
    return Optional.ofNullable(paramValues);
  }

  protected String errorOrderParam(String orderParamName) {
    return String.format("Parameter `%s` is invalid. Supported values are %s. Passed : `%s`\n",
        orderParamName,
        Arrays.stream(Order.values()).map(Order::name).collect(joining(", ")),
        param(orderParamName).orElse(null));
  }

  protected String errorMutuallyExclusivesParams(String param1, String param2) {
    return String.format("Parameters `%s` and `%s` can not be set both together.\n",
        param1,
        param2);
  }

  protected String errorNumberParam(String paramName) {
    return String.format("Parameter `%s` is invalid. `%s` is not a valid number.\n",
        paramName,
        param(paramName).orElse(null));
  }


  @Getter
  protected static class Page {
    private boolean valid;
    private Integer pageIndex;
    private Integer pageSize;

    protected Page(String rangeParameter) {
      if (rangeParameter == null) { // default page
        this.pageIndex = null;
        this.pageSize = null;
        this.valid = true;
      } else {
        String[] values = rangeParameter.split("-");
        if (values.length != 2) { // Invalid range format
          this.valid = false;
        } else {
          try {
            int firstIndex = Integer.valueOf(values[0]);
            int lastIndex = Integer.valueOf(values[1]);

            this.pageSize = lastIndex - firstIndex + 1;
            if (this.pageSize <= MAX_RANGE_LENGTH) {
              this.pageIndex = firstIndex / pageSize;

              this.valid = firstIndex % pageSize == 0; // First index must be a multiple of length
            } else {
              this.valid = false;
            }
          } catch (NumberFormatException e) {
            this.valid = false;
          }
        }
      }
    }
  }
}
