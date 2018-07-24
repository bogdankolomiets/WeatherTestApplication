package com.bogdankolomiets.weathertestapplication.repository;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CitiesRepository {
  @NonNull
  Single<List<UserCity>> getCities(int page);

  @NonNull
  Completable updateCity(UserCity userCity);

}
