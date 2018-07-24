package com.bogdankolomiets.weathertestapplication;

import android.app.Activity;
import android.app.Application;

import com.bogdankolomiets.weathertestapplication.di.ApiModule;
import com.bogdankolomiets.weathertestapplication.di.AppComponent;
import com.bogdankolomiets.weathertestapplication.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class WeatherApplication extends Application implements HasActivityInjector {
  protected AppComponent mAppComponent;

  @Inject
  DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

  @Override
  public void onCreate() {
    super.onCreate();
    resolveDependencies();
  }

  private void resolveDependencies() {
    mAppComponent = DaggerAppComponent.builder()
        .application(this)
        .apiModule(new ApiModule("https://api.openweathermap.org/data/2.5/"))
        .build();
    mAppComponent.inject(this);
  }

  @Override
  public AndroidInjector<Activity> activityInjector() {
    return mDispatchingAndroidInjector;
  }
}
