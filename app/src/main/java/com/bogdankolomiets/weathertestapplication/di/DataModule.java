package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.repository.CitiesRepository;
import com.bogdankolomiets.weathertestapplication.repository.CitiesRepositoryImpl;
import com.bogdankolomiets.weathertestapplication.repository.WeatherRepository;
import com.bogdankolomiets.weathertestapplication.repository.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface DataModule {

  @Binds
  @Singleton
  CitiesRepository provideCitiesRepository(CitiesRepositoryImpl citiesRepository);

  @Binds
  @Singleton
  WeatherRepository provideWeatherRepository(WeatherRepositoryImpl weatherRepository);
}
