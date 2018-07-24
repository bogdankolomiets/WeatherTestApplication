package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.repository.CitiesRepository;
import com.bogdankolomiets.weathertestapplication.repository.CitiesRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface DataModule {

  @Binds
  @Singleton
  CitiesRepository provideCitiesRepository(CitiesRepositoryImpl citiesRepository);
}
