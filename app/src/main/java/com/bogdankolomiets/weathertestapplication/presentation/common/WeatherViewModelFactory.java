package com.bogdankolomiets.weathertestapplication.presentation.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {
  private final @NonNull
  Map<Class<? extends ViewModel>, Provider<ViewModel>> mViewModels;

  @Inject
  public WeatherViewModelFactory(@NonNull Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels) {
    mViewModels = viewModels;
  }

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    Provider<ViewModel> viewModelProvider = mViewModels.get(modelClass);

    if (viewModelProvider == null) {
      throw new IllegalArgumentException("model class "
          + modelClass
          + " not found");
    }

    return (T) viewModelProvider.get();

  }
}
