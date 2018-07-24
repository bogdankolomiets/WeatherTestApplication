package com.bogdankolomiets.weathertestapplication.repository.model;

import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;

public class UserCity extends City {
  private boolean mSavedCity;

  public UserCity(Integer id, String name, String country) {
    super(id, name, country);
  }

  @Bindable
  public boolean isSavedCity() {
    return mSavedCity;
  }

  public void setSavedCity(boolean savedCity) {
    mSavedCity = savedCity;
    notifyPropertyChanged(BR.savedCity);
  }
}
