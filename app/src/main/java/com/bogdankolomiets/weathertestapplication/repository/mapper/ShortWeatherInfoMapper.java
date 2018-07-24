package com.bogdankolomiets.weathertestapplication.repository.mapper;

import com.bogdankolomiets.weathertestapplication.data.api.dto.MainWeatherInfoDto;
import com.bogdankolomiets.weathertestapplication.repository.model.ShortWeatherInfo;

import io.reactivex.functions.Function;


public class ShortWeatherInfoMapper implements Function<MainWeatherInfoDto, ShortWeatherInfo> {
  private static final String UNKNOWN_TAG = "Unknown";

  @Override
  public ShortWeatherInfo apply(MainWeatherInfoDto dto) throws Exception {
    String temperature = UNKNOWN_TAG;
    String minTemp = UNKNOWN_TAG;
    String maxTemp = UNKNOWN_TAG;
    if (dto.temp != null) {
      temperature = String.valueOf(Math.ceil(dto.temp));
    }
    if (dto.temp_min != null) {
      minTemp = String.valueOf(Math.ceil(dto.temp_min));
    }
    if (dto.temp_max != null) {
      maxTemp = String.valueOf(Math.ceil(dto.temp_max));
    }
    return new ShortWeatherInfo(temperature, minTemp, maxTemp);
  }
}
