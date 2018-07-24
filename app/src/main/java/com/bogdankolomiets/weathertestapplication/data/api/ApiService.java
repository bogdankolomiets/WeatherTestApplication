package com.bogdankolomiets.weathertestapplication.data.api;

import com.bogdankolomiets.weathertestapplication.data.api.dto.CityWeatherDto;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("weather/")
  Single<CityWeatherDto> getWeatherByCityId(
      @Query("id") Integer id,
      @Query("appid") String appId,
      @Query("units") String metrics,
      @Query("lang") String lang
  );
}
