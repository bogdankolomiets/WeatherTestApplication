package com.bogdankolomiets.weathertestapplication.repository;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.data.api.ApiServiceFacade;
import com.bogdankolomiets.weathertestapplication.data.api.dto.CityDto;
import com.bogdankolomiets.weathertestapplication.data.room.dao.CitiesDao;
import com.bogdankolomiets.weathertestapplication.data.room.enitity.CityEntity;
import com.bogdankolomiets.weathertestapplication.repository.mapper.UserCityMapper;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class CitiesRepositoryImpl implements CitiesRepository {
  private final @NonNull
  ApiServiceFacade mApiService;
  private final @NonNull
  CitiesDao mCitiesDao;
  private final @NonNull
  UserCityMapper mUserCityMapper;

  private static final int LIMIT = 20;

  @Inject
  public CitiesRepositoryImpl(
      @NonNull ApiServiceFacade apiService,
      @NonNull CitiesDao citiesDao,
      @NonNull UserCityMapper userCityMapper) {
    mApiService = apiService;
    mCitiesDao = citiesDao;
    mUserCityMapper = userCityMapper;
  }

  @NonNull
  @Override
  public Single<List<UserCity>> getCities(int page) {
    return Single.zip(getCitiesFromApi(page), getSavedCities(), mUserCityMapper);
  }

  @NonNull
  @Override
  public Completable updateCity(UserCity userCity) {
    return null;
  }

  private Single<List<CityDto>> getCitiesFromApi(int page) {
    return mApiService
        .getCities(page, LIMIT)
        .map(cityResponse -> cityResponse.data);
  }

  private Single<List<CityEntity>> getSavedCities() {
    return mCitiesDao.getSavedCities();
  }
}
