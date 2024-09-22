package com.example.sunnyweather;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.ViewModel.WeatherViewModel;
import com.example.sunnyweather.history.HistoryViewModel;

public class WeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final String cityName;
    private String cityNameZhen;
    private CityLocationViewModel cityLocationViewModel;
    private HistoryViewModel historyViewModel;
    private Activity activity;

    public WeatherViewModelFactory(String cityName, String cityNameZhen, CityLocationViewModel cityLocationViewModel
            , HistoryViewModel historyViewModel, Activity context) {
        this.cityName = cityName;
        this.cityNameZhen = cityNameZhen;
        this.cityLocationViewModel = cityLocationViewModel;
        this.historyViewModel = historyViewModel;
        this.activity = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            return (T) new WeatherViewModel(cityName, cityNameZhen,cityLocationViewModel,historyViewModel, activity);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
