package com.bogdankolomiets.weathertestapplication.data.api.dto;

import com.google.gson.annotations.SerializedName;

public class CityWeatherDto {
  @SerializedName("main")
  MainWeatherInfoDto mainWeatherInfo;
}
