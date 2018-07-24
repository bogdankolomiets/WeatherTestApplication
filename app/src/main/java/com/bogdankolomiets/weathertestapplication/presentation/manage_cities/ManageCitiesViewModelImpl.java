package com.bogdankolomiets.weathertestapplication.presentation.manage_cities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.Resource;
import com.bogdankolomiets.weathertestapplication.presentation.common.ReactiveViewModel;
import com.bogdankolomiets.weathertestapplication.repository.CitiesRepository;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageCitiesViewModelImpl extends ReactiveViewModel implements ManageCitiesViewModel {
  private final @NonNull
  CitiesRepository mCitiesRepository;

  private MutableLiveData<Resource<List<UserCity>>> mCitiesResult = new MutableLiveData<>();

  private MutableLiveData<Boolean> mMenuSaveEnabled = new MutableLiveData<>();

  private List<UserCity> mChanged = new LinkedList<>();

  private int mPage;

  @Inject
  public ManageCitiesViewModelImpl(@NonNull CitiesRepository citiesRepository) {
    mCitiesRepository = citiesRepository;
    mPage = 1;
    mMenuSaveEnabled.postValue(false);
  }

  @Override
  public void loadCities() {
    addDisposable(mCitiesRepository.getCities(mPage)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(__ -> mCitiesResult.setValue(Resource.loading(null)))
        .subscribe(data -> mCitiesResult.setValue(Resource.success(data)),
            e -> {
              e.printStackTrace();
              mCitiesResult.setValue(Resource.error(null, e.getLocalizedMessage()));
            }
        )
    );
  }

  @Override
  public LiveData<Boolean> menuEnabled() {
    return mMenuSaveEnabled;
  }

  public LiveData<Resource<List<UserCity>>> cities() {
    return mCitiesResult;
  }

  @Override
  public void onCityClicked(UserCity item) {
    addDisposable(Single.just(item)
        .map(city -> {
          city.setSavedCity(!city.isSavedCity());
          return city;
        })
        .map(city -> {
          boolean saved = city.isSavedCity();
          if (saved) {
            mChanged.add(city);
          } else {
            mChanged.remove(city);
          }
          return !mChanged.isEmpty();
        })
    .subscribe(mMenuSaveEnabled::postValue));
  }
}
