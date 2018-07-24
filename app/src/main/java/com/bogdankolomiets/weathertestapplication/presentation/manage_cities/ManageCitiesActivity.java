package com.bogdankolomiets.weathertestapplication.presentation.manage_cities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bogdankolomiets.weathertestapplication.R;
import com.bogdankolomiets.weathertestapplication.presentation.common.BaseActivity;
import com.bogdankolomiets.weathertestapplication.presentation.common.SingleDataBoundAdapterWithClickListener;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;
import com.google.gson.Gson;
import com.lonecrab.multistateview.MultiStateView;

import javax.inject.Inject;

import butterknife.BindView;

import static com.bogdankolomiets.weathertestapplication.utils.functions.Optional.ofNullable;

public class ManageCitiesActivity extends BaseActivity {

  @BindView(R.id.ac_manage_cities_msv)
  MultiStateView mMsvCities;
  @BindView(R.id.ac_manage_cities_rv_cities)
  RecyclerView mRvCities;

  @Inject
  ManageCitiesViewModel mViewModel;

  private final SingleDataBoundAdapterWithClickListener<UserCity> mAdapter
      = new SingleDataBoundAdapterWithClickListener<>(R.layout.item_user_city, this::onClick);


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ac_manage_cities);
    setupViews();
    mViewModel.cities().observe(this, result -> {
      switch (result.getStatus()) {
        case SUCCESS:
          mAdapter.show(result.getData());
      }
    });
    mViewModel.loadCities();
  }

  private void setupViews() {
    mRvCities.setAdapter(mAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_manage_cities, menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem menuItemSave = menu.findItem(R.id.menu_manage_cities_action_save);
    mViewModel.menuEnabled().observe(this, menuItemSave::setVisible);
    return super.onPrepareOptionsMenu(menu);
  }


  public void onClick(UserCity item) {
    ofNullable(mViewModel).ifPresent(vm -> vm.onCityClicked(item));
  }
}
