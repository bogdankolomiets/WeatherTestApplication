package com.bogdankolomiets.weathertestapplication.repository.model;

import android.databinding.BaseObservable;

public class City extends BaseObservable {
  private final Integer mId;
  private final String mName;
  private final String mCountry;

  public City(Integer id, String name, String country) {
    mId = id;
    mName = name;
    mCountry = country;
  }

  public Integer getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public String getCountry() {
    return mCountry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    City city = (City) o;

    return mId != null ? mId.equals(city.mId) : city.mId == null;
  }

  @Override
  public int hashCode() {
    return mId != null ? mId.hashCode() : 0;
  }
}
