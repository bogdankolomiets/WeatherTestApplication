package com.bogdankolomiets.weathertestapplication.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bogdankolomiets.weathertestapplication.data.room.enitity.CityEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CitiesDao {
  @Query("select * from cities")
  Single<List<CityEntity>> getSavedCities();

  @Insert
  void saveCity(CityEntity city);

  @Delete
  void deleteCity(CityEntity cityEntity);

}
