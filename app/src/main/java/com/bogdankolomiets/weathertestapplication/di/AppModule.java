package com.bogdankolomiets.weathertestapplication.di;

import android.content.Context;

import com.bogdankolomiets.weathertestapplication.WeatherApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  @Provides
  @Singleton
  Context provideContext(WeatherApplication application) {
    return application;
  }
}
