package com.bogdankolomiets.weathertestapplication.repository.model;

public class CityWeather {
  private final City mCity;
  private final ShortWeatherInfo mWeatherInfo;

  public CityWeather(City city, ShortWeatherInfo weatherInfo) {
    mCity = city;
    mWeatherInfo = weatherInfo;
  }

  public City getCity() {
    return mCity;
  }

  public ShortWeatherInfo getWeatherInfo() {
    return mWeatherInfo;
  }
}
