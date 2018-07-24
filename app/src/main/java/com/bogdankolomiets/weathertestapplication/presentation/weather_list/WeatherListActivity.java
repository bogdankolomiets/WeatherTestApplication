package com.bogdankolomiets.weathertestapplication.presentation.weather_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.bogdankolomiets.weathertestapplication.R;
import com.bogdankolomiets.weathertestapplication.presentation.common.BaseActivity;
import com.bogdankolomiets.weathertestapplication.presentation.manage_cities.ManageCitiesActivity;

public class WeatherListActivity extends BaseActivity {
  public static final int REQUEST_CODE_CITIES = 1;

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
}
