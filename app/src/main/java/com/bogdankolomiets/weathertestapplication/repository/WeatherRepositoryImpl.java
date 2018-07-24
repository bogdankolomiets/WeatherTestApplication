package com.bogdankolomiets.weathertestapplication.repository;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.data.api.ApiServiceFacade;
import com.bogdankolomiets.weathertestapplication.data.room.dao.CitiesDao;
import com.bogdankolomiets.weathertestapplication.repository.model.City;
import com.bogdankolomiets.weathertestapplication.repository.model.CityWeather;
import com.bogdankolomiets.weathertestapplication.repository.model.ShortWeatherInfo;
import com.bogdankolomiets.weathertestapplication.repository.model.Weather;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepositoryImpl implements WeatherRepository {
  private final @NonNull
  ApiServiceFacade mApiService;
  private final @NonNull
  CitiesDao mCitiesDao;

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
  public Single<List<CityWeather>> getSavedCitiesWithWeather() {
    return Flowable.fromIterable(mCitiesDao.getSavedCities().subscribeOn(Schedulers.io()).blockingGet())
        .map(cityEntity -> new City(cityEntity.id, cityEntity.name, cityEntity.country))
        .flatMapSingle(city -> mApiService.getWeatherByCityId(city.getId(), "d9c4fc71beb9c1607acfb8754daca0e6", "metric", "ru")
            .map(cityWeatherDto -> new ShortWeatherInfo())
            .map(shortWeatherInfo -> new CityWeather(city, shortWeatherInfo))
        )
        .toList();
  }
}
