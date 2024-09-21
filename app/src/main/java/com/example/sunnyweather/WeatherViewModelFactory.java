package com.example.sunnyweather;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sunnyweather.ViewModel.WeatherViewModel;

public class WeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final String cityName;

    public WeatherViewModelFactory(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            return (T) new WeatherViewModel(cityName);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
