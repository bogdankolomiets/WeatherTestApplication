package com.bogdankolomiets.weathertestapplication.repository.model;

public class City {
  private final String mId;
  private final String mName;
  private final String mCountry;

  public City(String id, String name, String country) {
    mId = id;
    mName = name;
    mCountry = country;
  }

  public String getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public String getCountry() {
    return mCountry;
  }
}
