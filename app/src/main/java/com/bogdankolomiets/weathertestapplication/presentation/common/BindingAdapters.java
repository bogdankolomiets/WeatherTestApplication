package com.bogdankolomiets.weathertestapplication.presentation.common;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

public class BindingAdapters {

//  @BindingAdapter({"android:decorator"})
  public static void addDecorator(RecyclerView rv, RecyclerView.ItemDecoration decorator) {
    rv.addItemDecoration(decorator);
  }

}
