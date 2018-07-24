package com.bogdankolomiets.weathertestapplication.utils.functions;

/**
 * A functional interface that takes a value and returns another value, possibly with a
 * different type and allows throwing a checked exception.
 *
 * @param <T> the input value type
 * @param <R> the output value type
 */
public interface Function<T, R> {
  /**
   * Applies this function to the given argument.
   *
   * @param t the function argument
   * @return the function result
   */
  R apply(T t);
}