package com.bogdankolomiets.weathertestapplication.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.bogdankolomiets.weathertestapplication.data.room.enitity.CityEntity;
import com.bogdankolomiets.weathertestapplication.utils.functions.Action;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class CitiesDao {
  @Query("select * from cities")
  public abstract Single<List<CityEntity>> getSavedCities();

  @Insert
  public abstract void saveCity(CityEntity city);

  @Delete
  public abstract void deleteCity(CityEntity cityEntity);

  @Transaction
  public void transaction(Action action) {
    action.run();
  }
}
