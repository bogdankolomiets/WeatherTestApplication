package com.bogdankolomiets.weathertestapplication.presentation.manage_cities;

import android.arch.lifecycle.LiveData;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

public interface ManageCitiesViewModel {
  void loadCities();
  LiveData<Boolean> menuEnabled();
  LiveData<Resource<List<UserCity>>> cities();

  void onCityClicked(UserCity item);
}
