package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.WeatherApplication;
import com.bogdankolomiets.weathertestapplication.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
    modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        RoomModule.class,
        ApiModule.class,
        ActivityBindingModule.class,
        ViewModelModule.class,
        DataModule.class
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
