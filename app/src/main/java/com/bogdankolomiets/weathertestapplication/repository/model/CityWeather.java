package com.bogdankolomiets.weathertestapplication.repository.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class CityWeather implements Parcelable {
  private final @NonNull City mCity;
  private final @NonNull ShortWeatherInfo mWeatherInfo;

  public CityWeather(@NonNull City city, @NonNull ShortWeatherInfo weatherInfo) {
    mCity = city;
    mWeatherInfo = weatherInfo;
  }

  protected CityWeather(Parcel in) {
    mCity = in.readParcelable(City.class.getClassLoader());
    mWeatherInfo = in.readParcelable(ShortWeatherInfo.class.getClassLoader());
  }

  public static final Creator<CityWeather> CREATOR = new Creator<CityWeather>() {
    @Override
    public CityWeather createFromParcel(Parcel in) {
      return new CityWeather(in);
    }

    @Override
    public CityWeather[] newArray(int size) {
      return new CityWeather[size];
    }
  };

  public City getCity() {
    return mCity;
  }

  public ShortWeatherInfo getWeatherInfo() {
    return mWeatherInfo;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(mCity, flags);
    dest.writeParcelable(mWeatherInfo, flags);
  }
}
