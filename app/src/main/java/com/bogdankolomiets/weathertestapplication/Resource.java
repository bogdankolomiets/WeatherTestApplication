package com.bogdankolomiets.weathertestapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.bogdankolomiets.weathertestapplication.Resource.Status.*;

public final class Resource<D> {
  private final @NonNull Status mStatus;
  private final @Nullable D mData;
  private final @Nullable String mErrorMessage;

  private Resource(@NonNull Status status, @Nullable D data) {
    this(status, data, null);
  }

  private Resource(@NonNull Status status, @Nullable D data, @Nullable String errorMessage) {
    mStatus = status;
    mData = data;
    mErrorMessage = errorMessage;
  }

  @NonNull
  public static <D> Resource<D> success(@Nullable D data) {
    return new Resource<>(SUCCESS, data);
  }

  @NonNull
  public static <D> Resource<D> error(@Nullable D data, @Nullable String message) {
    return new Resource<>(ERROR, data, message);
  }

  @NonNull
  public static <D> Resource<D> loading(@Nullable D data) {
    return new Resource<>(LOADING, data);
  }

  public @NonNull Status getStatus() {
    return mStatus;
  }

  @Nullable
  public D getData() {
    return mData;
  }

  @Nullable
  public String getErrorMessage() {
    return mErrorMessage;
  }

  public enum Status {
    LOADING,
    SUCCESS,
    ERROR
  }
}
