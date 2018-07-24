package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.presentation.common.ReactiveViewModel;
import com.bogdankolomiets.weathertestapplication.repository.CitiesRepository;
import com.bogdankolomiets.weathertestapplication.repository.WeatherRepository;
import com.bogdankolomiets.weathertestapplication.repository.model.CityWeather;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherListViewModelImpl extends ReactiveViewModel implements WeatherListViewModel {
  private final @NonNull
  WeatherRepository mWeatherRepository;
  private final @NonNull
  CitiesRepository mCitiesRepository;

  private final MutableLiveData<Resource<List<CityWeather>>> mWeather = new MutableLiveData<Resource<List<CityWeather>>>() {
    @Override
    protected void onActive() {
      loadWeather();
    }
  };

  @Inject
  public WeatherListViewModelImpl(@NonNull CitiesRepository citiesRepository, @NonNull WeatherRepository weatherRepository) {
    mCitiesRepository = citiesRepository;
    mWeatherRepository = weatherRepository;
  }

  @Override
  public LiveData<Resource<List<CityWeather>>> weather() {
    return mWeather;
  }

  @Override
  public void handleChangeCitiesResult(List<UserCity> cities) {
    addDisposable(mCitiesRepository.updateCities(cities)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Log.d("WeatherListViewModel", "success")));
  }

  private void loadWeather() {
    addDisposable(mWeatherRepository.getSavedCitiesWithWeather()
        .repeatWhen(completed -> completed.delay(1, TimeUnit.MINUTES))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(__ -> mWeather.setValue(Resource.loading(null)))
        .subscribe(data -> mWeather.setValue(Resource.success(data)),
            e -> {
              e.printStackTrace();
              mWeather.setValue(Resource.error(null, e.getLocalizedMessage()));
            }));
  }
}
