package com.bogdankolomiets.weathertestapplication.repository.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ShortWeatherInfo implements Parcelable {
  private final @NonNull String mTemperature;
  private final @NonNull String mMin;
  private final @NonNull String mMax;

  public ShortWeatherInfo(@NonNull String temperature, @NonNull String min, @NonNull String max) {
    mTemperature = temperature;
    mMin = min;
    mMax = max;
  }

  protected ShortWeatherInfo(Parcel in) {
    mTemperature = in.readString();
    mMin = in.readString();
    mMax = in.readString();
  }

  public static final Creator<ShortWeatherInfo> CREATOR = new Creator<ShortWeatherInfo>() {
    @Override
    public ShortWeatherInfo createFromParcel(Parcel in) {
      return new ShortWeatherInfo(in);
    }

    @Override
    public ShortWeatherInfo[] newArray(int size) {
      return new ShortWeatherInfo[size];
    }
  };

  @NonNull
  public String getTemperature() {
    return mTemperature;
  }

  @NonNull
  public String getMin() {
    return mMin;
  }

  @NonNull
  public String getMax() {
    return mMax;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mTemperature);
    dest.writeString(mMin);
    dest.writeString(mMax);
  }
}
