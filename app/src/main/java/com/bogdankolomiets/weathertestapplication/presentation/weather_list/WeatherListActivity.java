package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.bogdankolomiets.weathertestapplication.R;
import com.bogdankolomiets.weathertestapplication.presentation.common.BaseActivity;
import com.bogdankolomiets.weathertestapplication.presentation.common.SingleDataBoundAdapterWithClickListener;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesActivity;
import com.bogdankolomiets.weathertestapplication.repository.model.CityWeather;
import com.bogdankolomiets.weathertestapplication.repository.model.ShortWeatherInfo;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class WeatherListActivity extends BaseActivity {
  public static final int REQUEST_CODE_CITIES = 1;
  public static final String KEY_CHANGED_CITIES = "key_changed_cities";

  @Inject
  WeatherListViewModel mViewModel;

  @BindView(R.id.ac_weather_list_rv_weather)
  RecyclerView mRvWeather;

  private final SingleDataBoundAdapterWithClickListener<CityWeather> mWeatherAdapter
      = new SingleDataBoundAdapterWithClickListener<>(R.layout.item_weather_short, v -> {});

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ac_weather_list);
    setupViews();
    mViewModel.weather().observe(this, resource -> {
      switch (resource.getStatus()) {
        case SUCCESS:
          mWeatherAdapter.show(resource.getData());
          break;
      }
    });
  }

  private void setupViews() {
    mRvWeather.setAdapter(mWeatherAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_weather_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_weather_list_action_open_manage_cities:
        startActivityForResult(new Intent(this, ManageCitiesActivity.class), REQUEST_CODE_CITIES);
        return true;
      default:
        return false;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_CITIES) {
      if (resultCode == RESULT_OK) {
        mViewModel.handleChangeCitiesResult(data.getParcelableArrayListExtra(KEY_CHANGED_CITIES));
      }
    }
  }
}
