package com.bogdankolomiets.weathertestapplication.di;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bogdankolomiets.weathertestapplication.data.room.WeatherDatabase;
import com.bogdankolomiets.weathertestapplication.data.room.dao.CitiesDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

  @Provides
  @Singleton
  public WeatherDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context, WeatherDatabase.class, WeatherDatabase.NAME).build();
  }

  @Provides
  @Singleton
  public CitiesDao provideCitiesDao(WeatherDatabase database) {
    return database.citiesDao();
  }
}
