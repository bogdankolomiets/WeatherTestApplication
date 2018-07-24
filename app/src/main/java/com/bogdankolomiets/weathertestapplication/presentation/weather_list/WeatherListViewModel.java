package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

public interface WeatherListViewModel {
  void handleChangeCitiesResult(List<UserCity> cities);
}
