package com.bogdankolomiets.weathertestapplication.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResourceManager {
  private final @NonNull Context mContext;

  @Inject
  public ResourceManager(@NonNull Context context) {
    mContext = context;
  }

  public String getString(@StringRes int resId) {
    return mContext.getString(resId);
  }

  public String getString(@StringRes int resId, Object... formatArgs) {
    return mContext.getString(resId, formatArgs);
  }

}
