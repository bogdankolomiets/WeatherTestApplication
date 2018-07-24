package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import android.arch.lifecycle.LiveData;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.repository.model.CityWeather;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

public interface WeatherListViewModel {
  LiveData<Resource<List<CityWeather>>> weather();
  void handleChangeCitiesResult(List<UserCity> cities);
}
