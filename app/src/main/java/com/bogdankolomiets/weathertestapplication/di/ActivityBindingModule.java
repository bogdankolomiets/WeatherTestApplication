package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.di.modules.ManageCitiesActivityModule;
import com.bogdankolomiets.weathertestapplication.di.scopes.ActivityScope;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBindingModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = ManageCitiesActivityModule.class)
  ManageCitiesActivity contributeManageCitiesActivity();
}
