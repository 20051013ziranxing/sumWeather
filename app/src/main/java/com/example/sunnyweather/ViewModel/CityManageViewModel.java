package com.example.sunnyweather.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.Adapter.CityAdapter;
import com.example.sunnyweather.Location;
import com.example.sunnyweather.WeatherHome;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.LicationData;
import com.example.sunnyweather.bean.sumWeather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CityManageViewModel extends ViewModel {
    /*private static final String TAG = "MainActivity";
    private MutableLiveData<List<CityAdapter.CityItem>> cityList = new MutableLiveData<>();

    public LiveData<List<CityAdapter.CityItem>> getCityList() {
        return cityList;
    }

    public CityManageViewModel() {
    }
    public void addCity(String cityName) {
        CityAdapter.CityItem cityItem = new CityAdapter.CityItem(cityName);
        List<CityAdapter.CityItem> cityList1 = cityList.getValue();
        int temp = 0;
        if (cityList1 == null) {
            cityList1 = new ArrayList<>();
        }
        for (int i = 0; i < cityList1.size(); i++) {
            CityAdapter.CityItem city = cityList1.get(i);
            if (city.getCityName().equals(cityName)) {
                temp = 1;
            }
        }
        if (temp == 0) {
            cityList1.add(cityItem);
        }
        cityList.postValue(cityList1);
    }

    public void removeCity(String cityName) {
        List<CityAdapter.CityItem> cityList1 = new ArrayList<>(cityList.getValue());
        for (int i = 0; i < cityList1.size(); ) {
            CityAdapter.CityItem city = cityList1.get(i);
            if (city.getCityName().equals(cityName)) {
                cityList1.remove(i);
            } else {
                i++;
            }
        }
        cityList.setValue(cityList1);
    }*/
}
