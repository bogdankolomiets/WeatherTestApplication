package com.bogdankolomiets.weathertestapplication.presentation.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

public class SingleDataBoundAdapter<T> extends RecyclerView.Adapter<SingleDataBoundAdapter.DataBoundHolder<T>> {
  private List<T> mItems;
  private final @LayoutRes
  int mLayoutRes;

  public SingleDataBoundAdapter(@LayoutRes int layoutRes) {
    mItems = new ArrayList<>();
    mLayoutRes = layoutRes;
  }

  @NonNull
  @Override
  public DataBoundHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new DataBoundHolder<>(DataBindingUtil.inflate(inflater, mLayoutRes, parent, false ));
  }

  @Override
  public void onBindViewHolder(@NonNull DataBoundHolder<T> holder, int position) {
    holder.bind(mItems.get(position));
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  public void show(List<T> items) {
    DiffUtil.DiffResult diffResult =
        DiffUtil.calculateDiff(new SingleDiffUtilCallback<>(mItems, items));
    mItems = items;
    diffResult.dispatchUpdatesTo(this);
  }

  static class DataBoundHolder<T> extends RecyclerView.ViewHolder {
    private final ViewDataBinding mBinding;

    DataBoundHolder(ViewDataBinding binding) {
      super(binding.getRoot());
      mBinding = binding;
    }

    void bind(T item) {
      mBinding.setVariable(BR.item, item);
      mBinding.executePendingBindings();
    }

    public ViewDataBinding getBinding() {
      return mBinding;
    }
  }
}
