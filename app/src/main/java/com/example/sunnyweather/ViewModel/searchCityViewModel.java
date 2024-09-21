package com.example.sunnyweather.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.Location;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.searchCity;
import com.example.sunnyweather.bean.sumWeather;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class searchCityViewModel extends ViewModel {
    private static final String TAG = "MainActivity";
    String key = "ffa53b25cd8947c2a0740c3be0c266f2";
    private String searchcityName;
    private MutableLiveData<searchCity> CityData;
    public searchCityViewModel() {
        CityData = new MutableLiveData<>();
        /*this.searchcityName = searchcityName;
        CityData = new MutableLiveData<>();
        loadCityData();*/
    }
    public void setQuery(String searchCityName) {
        this.searchcityName = searchCityName;
        loadCityData();
    }
    public LiveData<searchCity> getWeatherData() {
        return CityData;
    }
    public void loadCityData() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            searchCity searchCity;
            String Url = "https://geoapi.qweather.com/v2/city/lookup?key=" + key + "&location="+ searchcityName;
            searchCity = (parseJSONWith_city(client, Url));
            CityData.postValue(searchCity);
        }).start();
    }
    private searchCity parseJSONWith_city(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, responseBody);
                return new Gson().fromJson(responseBody, searchCity.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取城市搜索失败", e);
        }
        return null;
    }
}
