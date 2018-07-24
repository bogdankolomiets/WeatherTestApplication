package com.bogdankolomiets.weathertestapplication.repository;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.repository.model.CityWeather;
import com.bogdankolomiets.weathertestapplication.repository.model.Weather;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface WeatherRepository {

  @NonNull
  Observable<Resource<Weather>> getWeatherByCityId(String id);

  @NonNull
  Single<List<CityWeather>> getSavedCitiesWithWeather();
}
