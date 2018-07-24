package com.bogdankolomiets.weathertestapplication.repository.model;

import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.databinding.library.baseAdapters.BR;

public class UserCity extends City implements Parcelable {
  private boolean mSavedCity;

  public UserCity(Integer id, String name, String country) {
    super(id, name, country);
  }

  protected UserCity(Parcel in) {
    super(in);
    mSavedCity = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeByte((byte) (mSavedCity ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UserCity> CREATOR = new Creator<UserCity>() {
    @Override
    public UserCity createFromParcel(Parcel in) {
      return new UserCity(in);
    }

    @Override
    public UserCity[] newArray(int size) {
      return new UserCity[size];
    }
  };

  @Bindable
  public boolean isSavedCity() {
    return mSavedCity;
  }

  public void setSavedCity(boolean savedCity) {
    mSavedCity = savedCity;
    notifyPropertyChanged(BR.savedCity);
  }


}
