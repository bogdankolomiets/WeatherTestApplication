package com.bogdankolomiets.weathertestapplication.repository.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class City extends BaseObservable implements Parcelable {
  private final Integer mId;
  private final String mName;
  private final String mCountry;

  public City(Integer id, String name, String country) {
    mId = id;
    mName = name;
    mCountry = country;
  }

  protected City(Parcel in) {
    if (in.readByte() == 0) {
      mId = null;
    } else {
      mId = in.readInt();
    }
    mName = in.readString();
    mCountry = in.readString();
  }

  public static final Creator<City> CREATOR = new Creator<City>() {
    @Override
    public City createFromParcel(Parcel in) {
      return new City(in);
    }

    @Override
    public City[] newArray(int size) {
      return new City[size];
    }
  };

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (mId == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(mId);
    }
    dest.writeString(mName);
    dest.writeString(mCountry);
  }
}
