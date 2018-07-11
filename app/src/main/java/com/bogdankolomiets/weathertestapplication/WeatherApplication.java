package com.bogdankolomiets.weathertestapplication;

import android.app.Application;

import com.bogdankolomiets.weathertestapplication.di.AppComponent;
import com.bogdankolomiets.weathertestapplication.di.DaggerAppComponent;

public class WeatherApplication extends Application {
  protected AppComponent mAppComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    resolveDependencies();
  }

  private void resolveDependencies() {
    mAppComponent = DaggerAppComponent.builder()
        .application(this)
        .build();
    mAppComponent.inject(this);
  }
}
