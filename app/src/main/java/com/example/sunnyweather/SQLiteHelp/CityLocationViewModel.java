package com.example.sunnyweather.SQLiteHelp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CityLocationViewModel extends AndroidViewModel {
    //当要使用上下文的时候使用AndroidViewModel
    private CityLocationRepository cityLocationRepository;
    public CityLocationViewModel(@NonNull Application application) {
        super(application);
        this.cityLocationRepository = new CityLocationRepository(application);
    }
    public void updatecityLocation(CityLocation... cityLocations) {
        cityLocationRepository.updateCityLocation(cityLocations);
    }
    public void insertcityLocation(CityLocation... cityLocations) {
        cityLocationRepository.insertCityLocation(cityLocations);
    }
    public void deletecityLocation(CityLocation... cityLocations) {
        Log.d("MainActivity1", "没想到吧，我执行到了VIewModel");
        cityLocationRepository.deleteCityLocation(cityLocations);
    }
    /*public void deleteAllStudent() {
        cityLocationRepository.deleteAllcityLocations();
    }*/
    public LiveData<List<CityLocation>> research() {
        return cityLocationRepository.getAllCityLocationLive();
    }
    /*public LiveData<CityLocation> researchhh(String cityName) {
        return cityLocationRepository.getCityLocationByName(cityName);
    }*/
    public LiveData<CityLocation> researchhh(String cityName) {
        return cityLocationRepository.getCityLocationByName(cityName);
    }
    public CityLocation researchhhname(String cityName) {
        return cityLocationRepository.getCityLocationByNamename(cityName);
    }
}
