package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.WeatherApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
    modules = {
        AndroidSupportInjectionModule.class,
        RoomModule.class,
        ApiModule.class

    })
public interface AppComponent {

  void inject(WeatherApplication application);

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(WeatherApplication app);

    Builder apiModule(ApiModule apiModule);

    AppComponent build();
  }
}
