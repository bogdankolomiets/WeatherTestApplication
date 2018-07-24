package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.di.modules.ManageCitiesActivityModule;
import com.bogdankolomiets.weathertestapplication.di.modules.WeatherListActivityModule;
import com.bogdankolomiets.weathertestapplication.di.scopes.ActivityScope;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesActivity;
import com.bogdankolomiets.weathertestapplication.presentation.weather_list.WeatherListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBindingModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = ManageCitiesActivityModule.class)
  ManageCitiesActivity contributeManageCitiesActivity();

  @ActivityScope
  @ContributesAndroidInjector(modules = WeatherListActivityModule.class)
  WeatherListActivity contributeWeatherListActivity();
}
