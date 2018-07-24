package com.bogdankolomiets.weathertestapplication.data.api;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.data.api.dto.CityDto;
import com.bogdankolomiets.weathertestapplication.data.api.dto.CityResponse;
import com.bogdankolomiets.weathertestapplication.data.api.dto.CityWeatherDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Query;

public interface ApiServiceFacade {

  @NonNull
  Single<CityWeatherDto> getWeatherByCityId(
      @Query("id") Integer id,
      @Query("appid") String appId,
      @Query("units") String metrics,
      @Query("lang") String lang
  );


  @NonNull
  Single<CityResponse> getCities(int page, int limit);
}
