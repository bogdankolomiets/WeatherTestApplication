package com.bogdankolomiets.weathertestapplication.data.api;

import android.support.annotation.NonNull;

import com.bogdankolomiets.weathertestapplication.data.api.dto.CityDto;
import com.bogdankolomiets.weathertestapplication.data.api.dto.CityResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class FirebaseApiService {
  private final @NonNull FirebaseDatabase mFirebaseDatabase;

  private final static String PATH_CITIES = "cities";

  @Inject
  public FirebaseApiService(@NonNull FirebaseDatabase firebaseDatabase) {
    mFirebaseDatabase = firebaseDatabase;
  }

  public Single<CityResponse> getCities(int page, int limit) {
    if (page <= 0) {
      return Single.error(new IllegalArgumentException("page can`t be less than 1"));
    }
    if (limit <= 0) {
      return Single.error(new IllegalArgumentException("limit can`t be less than 1"));
    }
    return Single.create(e -> {
      mFirebaseDatabase
          .getReference("cities")
          .limitToLast(page * limit)
          .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              CityResponse response = new CityResponse();
              List<CityDto> cities = new ArrayList<>();
              for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                CityDto cityDto = childSnapshot.getValue(CityDto.class);
                cities.add(cityDto);
              }
              response.data = cities;
              e.onSuccess(response);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              e.onError(databaseError.toException());
            }
          });
    });
  }
}
