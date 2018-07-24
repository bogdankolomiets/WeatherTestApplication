package com.bogdankolomiets.weathertestapplication.presentation.common;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class ReactiveViewModel extends ViewModel {
  private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

  public ReactiveViewModel() {

  }

  @Override
  protected void onCleared() {
    mCompositeDisposable.clear();
  }

  protected void addDisposable(@NonNull Disposable disposable) {
    mCompositeDisposable.add(disposable);
  }
}
