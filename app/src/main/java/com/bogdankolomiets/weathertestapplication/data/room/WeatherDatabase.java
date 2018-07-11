package com.bogdankolomiets.weathertestapplication.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bogdankolomiets.weathertestapplication.data.room.dao.CitiesDao;
import com.bogdankolomiets.weathertestapplication.data.room.enitity.CityEntity;

import static com.bogdankolomiets.weathertestapplication.data.room.WeatherDatabase.VERSION;

@Database(entities = {CityEntity.class}, version = VERSION)
public abstract class WeatherDatabase extends RoomDatabase {
  public static final String NAME = "weather-db";
  static final int VERSION = 1;

  public abstract CitiesDao citiesDao();
}
