package com.bogdankolomiets.weathertestapplication.presentation.common;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class SingleDiffUtilCallback<T> extends DiffUtil.Callback {
  private final List<T> mOldItems;
  private final List<T> mNewItems;

  public SingleDiffUtilCallback(List<T> oldItems, List<T> newItems) {
    mOldItems = oldItems;
    mNewItems = newItems;
  }

  @Override
  public int getOldListSize() {
    return mOldItems.size();
  }

  @Override
  public int getNewListSize() {
    return mNewItems.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return mOldItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return mNewItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
  }
}
