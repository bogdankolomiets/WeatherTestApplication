package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bogdankolomiets.weathertestapplication.presentation.common.ReactiveViewModel;
import com.bogdankolomiets.weathertestapplication.repository.CitiesRepository;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherListViewModelImpl extends ReactiveViewModel implements WeatherListViewModel {
  private final @NonNull
  CitiesRepository mCitiesRepositoryl;

  @Inject
  public WeatherListViewModelImpl(@NonNull CitiesRepository citiesRepository) {
    mCitiesRepositoryl = citiesRepository;
  }

  @Override
  public void handleChangeCitiesResult(List<UserCity> cities) {
    addDisposable(mCitiesRepositoryl.updateCities(cities)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Log.d("WeatherListViewModel", "success")));
  }
}
