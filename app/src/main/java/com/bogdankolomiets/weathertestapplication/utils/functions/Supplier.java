package com.bogdankolomiets.weathertestapplication.utils.functions;

public interface Supplier<T> {

  /**
   * Gets a result.
   *
   * @return a result
   */
  T get();
}
