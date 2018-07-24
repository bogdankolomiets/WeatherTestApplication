package com.bogdankolomiets.weathertestapplication.repository.mapper;

import com.bogdankolomiets.weathertestapplication.data.api.dto.CityDto;
import com.bogdankolomiets.weathertestapplication.data.room.enitity.CityEntity;
import com.bogdankolomiets.weathertestapplication.repository.model.UserCity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;


public class UserCityMapper implements BiFunction<List<CityDto>, List<CityEntity>, List<UserCity>> {

  @Inject
  public UserCityMapper() {

  }

  @Override
  public List<UserCity> apply(List<CityDto> cityDtos, List<CityEntity> cityEntities) throws Exception {
    return Observable.fromIterable(cityDtos)
        .map(cityDto -> new UserCity(cityDto.id, cityDto.name, cityDto.country))
        .map(userCity -> {
          userCity.setSavedCity(contains(cityEntities, userCity));
          return userCity;
        })
        .toList()
        .blockingGet();
  }

  private boolean contains(List<CityEntity> entities, UserCity userCity) {
    for (CityEntity entity : entities) {
      if (entity.id.equals(userCity.getId())) {
        return true;
      }
    }
    return false;
  }
}
