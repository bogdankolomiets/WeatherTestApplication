package com.bogdankolomiets.weathertestapplication.presentation.common;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import com.android.databinding.library.baseAdapters.BR;
public class SingleDataBoundAdapterWithClickListener<T> extends SingleDataBoundAdapter<T> {
  private final @NonNull OnItemClickListener<T> mOnItemClickListener;

  public SingleDataBoundAdapterWithClickListener(@LayoutRes int layoutRes, @NonNull OnItemClickListener<T> listener) {
    super(layoutRes);
    mOnItemClickListener = listener;
  }

  @Override
  public void onBindViewHolder(@NonNull DataBoundHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.getBinding().setVariable(BR.clickListener, mOnItemClickListener);
    holder.getBinding().executePendingBindings();
  }

  public interface OnItemClickListener<T> {
    void onClick(T item);
  }
}
