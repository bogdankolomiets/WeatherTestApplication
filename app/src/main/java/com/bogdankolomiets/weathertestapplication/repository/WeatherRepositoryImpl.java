package com.bogdankolomiets.weathertestapplication.repository;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.data.api.ApiServiceFacade;
import com.bogdankolomiets.weathertestapplication.data.room.dao.CitiesDao;
import com.bogdankolomiets.weathertestapplication.repository.model.CityWeather;
import com.bogdankolomiets.weathertestapplication.repository.model.Weather;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {
  private final @NonNull ApiServiceFacade mApiService;
  private final @NonNull CitiesDao mCitiesDao;

  @Inject
  public WeatherRepositoryImpl(@NonNull ApiServiceFacade apiService, @NonNull CitiesDao citiesDao) {
    mApiService = apiService;
    mCitiesDao = citiesDao;
  }

  @NonNull
  @Override
  public Observable<Resource<Weather>> getWeatherByCityId(String id) {
    return null;
  }

  @NonNull
  @Override
  public Observable<Resource<CityWeather>> getSavedCitiesWithWeather() {
    return null;
  }
}
