package com.bogdankolomiets.weathertestapplication.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bogdankolomiets.weathertestapplication.data.room.dao.CitiesDao;

@Database(entities = {CitiesDao.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

  public abstract CitiesDao citiesDao();
}
