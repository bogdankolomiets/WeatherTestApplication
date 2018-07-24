package com.bogdankolomiets.weathertestapplication.data.api;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.data.api.dto.CityResponse;
import com.bogdankolomiets.weathertestapplication.data.api.dto.CityWeatherDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ApiServiceFacadeImpl implements ApiServiceFacade {
  private final @NonNull ApiService mApiService;
  private final @NonNull FirebaseApiService mFirebaseApiService;

  @Inject
  public ApiServiceFacadeImpl(@NonNull ApiService apiService, @NonNull FirebaseApiService firebaseApiService) {
    mApiService = apiService;
    mFirebaseApiService = firebaseApiService;
  }

  @NonNull
  @Override
  public Single<CityWeatherDto> getWeatherByCityId(Integer id, String appId, String metrics, String lang) {
    return mApiService.getWeatherByCityId(
        id,
        appId,
        metrics,
        lang
    );
  }

  @NonNull
  @Override
  public Single<CityResponse> getCities(int page, int limit) {
    return mFirebaseApiService.getCities(page, limit);
  }
}
