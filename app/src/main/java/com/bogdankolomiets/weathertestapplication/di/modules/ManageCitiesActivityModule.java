package com.bogdankolomiets.weathertestapplication.di.modules;

import android.arch.lifecycle.ViewModelProviders;

import com.bogdankolomiets.weathertestapplication.di.ViewModelKey;
import com.bogdankolomiets.weathertestapplication.di.scopes.ActivityScope;
import com.bogdankolomiets.weathertestapplication.presentation.common.WeatherViewModelFactory;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesActivity;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesViewModel;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesViewModelImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public interface ManageCitiesActivityModule {

  @Provides
  static ManageCitiesViewModel provideManageCitiesViewModel(WeatherViewModelFactory vmFactory,
                                                     ManageCitiesActivity activity) {
    return ViewModelProviders
        .of(activity, vmFactory)
        .get(ManageCitiesViewModelImpl.class);
  }

}
