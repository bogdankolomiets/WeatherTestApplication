package com.bogdankolomiets.weathertestapplication.di.modules;

import android.arch.lifecycle.ViewModel;

import com.bogdankolomiets.weathertestapplication.di.ViewModelKey;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesViewModelImpl;
import com.bogdankolomiets.weathertestapplication.presentation.weather_list.WeatherListViewModelImpl;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(ManageCitiesViewModelImpl.class)
  ViewModel manageCitiesViewModel(ManageCitiesViewModelImpl manageCitiesViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(WeatherListViewModelImpl.class)
  ViewModel weatherListViewModel(WeatherListViewModelImpl manageCitiesViewModel);
}
