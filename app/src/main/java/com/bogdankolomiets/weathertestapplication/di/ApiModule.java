package com.bogdankolomiets.weathertestapplication.di;

import com.bogdankolomiets.weathertestapplication.BuildConfig;
import com.bogdankolomiets.weathertestapplication.data.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
  private static final String LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR";

  private final String mBaseUrl;

  public ApiModule(String baseUrl) {
    mBaseUrl = baseUrl;
  }

  @Provides
  @Singleton
  Gson provideGson() {
    return new GsonBuilder().setLenient().create();
  }

  @Provides
  @Singleton
  GsonConverterFactory provideGsonConverterFactory(Gson gson) {
    return GsonConverterFactory.create(gson);
  }

  @Provides
  @Singleton
  RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  @Named(LOGGING_INTERCEPTOR)
  Interceptor provideLogginINterceptor() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    return loggingInterceptor;
  }

  @Singleton
  @Provides
  OkHttpClient provideOkHttpClient(@Named(LOGGING_INTERCEPTOR) Interceptor loggingInterceptor) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS);

    if (BuildConfig.DEBUG) {
      builder.addInterceptor(loggingInterceptor);
    }

    return builder.build();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(
      OkHttpClient okHttpClient,
      GsonConverterFactory gsonConverterFactory,
      RxJava2CallAdapterFactory rxJava2CallAdapterFactory
  ) {
    return new Retrofit.Builder()
        .baseUrl(mBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build();
  }

  @Provides
  @Singleton
  ApiService provideApiService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }

}
