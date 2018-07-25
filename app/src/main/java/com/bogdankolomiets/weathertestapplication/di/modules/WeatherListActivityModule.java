package com.bogdankolomiets.weathertestapplication.di.modules;

import android.arch.lifecycle.ViewModelProviders;

import com.bogdankolomiets.weathertestapplication.di.scopes.ActivityScope;
import com.bogdankolomiets.weathertestapplication.presentation.common.WeatherViewModelFactory;
import com.bogdankolomiets.weathertestapplication.presentation.weather_list.WeatherListActivity;
import com.bogdankolomiets.weathertestapplication.presentation.weather_list.WeatherListViewModel;
import com.bogdankolomiets.weathertestapplication.presentation.weather_list.WeatherListViewModelImpl;

import dagger.Module;
import dagger.Provides;

@Module
public interface WeatherListActivityModule {

  @Provides
  static WeatherListViewModel provideManageCitiesViewModel(WeatherViewModelFactory vmFactory,
                                                           WeatherListActivity activity) {
    return ViewModelProviders
        .of(activity, vmFactory)
        .get(WeatherListViewModelImpl.class);
  }
}
