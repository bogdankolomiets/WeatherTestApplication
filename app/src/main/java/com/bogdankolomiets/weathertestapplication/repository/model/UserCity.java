package com.bogdankolomiets.weathertestapplication.repository.model;

public class UserCity extends City {
  private boolean mSavedCity;

  public UserCity(String id, String name, String country) {
    super(id, name, country);
  }

  public boolean isSavedCity() {
    return mSavedCity;
  }

  public void setSavedCity(boolean savedCity) {
    mSavedCity = savedCity;
  }
}
