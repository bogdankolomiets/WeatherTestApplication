package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.bogdankolomiets.weathertestapplication.R;
import com.bogdankolomiets.weathertestapplication.presentation.common.BaseActivity;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesActivity;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

import javax.inject.Inject;

public class WeatherListActivity extends BaseActivity {
  public static final int REQUEST_CODE_CITIES = 1;
  public static final String KEY_CHANGED_CITIES = "key_changed_cities";

  @Inject
  WeatherListViewModel mViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ac_weather_list);
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
