package com.bogdankolomiets.weathertestapplication.data.room.enitity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cities")
public class CityEntity {
  @PrimaryKey
  public int id;

  public String name;

  public String country;

}
