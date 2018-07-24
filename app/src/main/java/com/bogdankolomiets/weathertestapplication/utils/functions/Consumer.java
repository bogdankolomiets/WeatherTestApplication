package com.bogdankolomiets.weathertestapplication.utils.functions;

/**
 * A functional interface (callback) that accepts a single value.
 *
 * @param <T> the value type
 */
public interface Consumer<T> {
  /**
   * Performs this operation on the given argument.
   *
   * @param t the input argument
   */
  void accept(T t);
}
