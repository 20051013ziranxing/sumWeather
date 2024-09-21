package com.example.sunnyweather.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.Location;
import com.example.sunnyweather.bean.AirNumber;
import com.example.sunnyweather.bean.AirQuality;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.NowWea;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.bean.sumWeather;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "MainActivity";
    String key = "ffa53b25cd8947c2a0740c3be0c266f2";
    String key1 = "137d267285e8499dbaa571bfb8049b22";
    private String cityName;
    private MutableLiveData<sumWeather> weatherData;

    public WeatherViewModel(String cityName) {
        this.cityName = cityName;
        weatherData = new MutableLiveData<>();
        try {
            loadWeatherData();
        } catch (NullPointerException e) {
            Log.d(TAG, "解析出错");
            e.printStackTrace();
        }
    }

    public void setWeatherData(sumWeather data) {
        weatherData.postValue(data);
    }

    public LiveData<sumWeather> getWeatherData() {
        return weatherData;
    }

    public void loadWeatherData() {
        Location location = new Location();
        location.Locationoo(cityName, new Location.OnLocationRetrievedListener() {
            @Override
            public void onLocationRetrieved(String locationid) {
                Log.d(TAG, "我得到了" + locationid);
                new Thread(() -> {
                    OkHttpClient client = new OkHttpClient();
                    sumWeather sumWeather = new sumWeather();
                    String dayUrl = "https://devapi.qweather.com/v7/weather/7d?key=" + key + "&location="+ locationid;
                    sumWeather.setDay(parseJSONWith_day(client, dayUrl));
                    String hourUrl = "https://devapi.qweather.com/v7/weather/24h?key=" + key + "&location="+ locationid;
                    sumWeather.setHour24(parseJSONWith_24(client, hourUrl));
                    String nowUrl = "https://devapi.qweather.com/v7/weather/now?key=" + key + "&location="+ locationid;
                    sumWeather.setNowWea(parseJSONWith_now(client, nowUrl));
                    String airUrl = "https://devapi.qweather.com/v7/indices/1d?type=1,2,3,4,6,9&key=" + key + "&location="+ locationid;
                    sumWeather.setAirnumber(parseJSONWith_air(client, airUrl));
                    /*Log.d(TAG, "年间多次" + sumWeather.getAirnumber().getDaily().get(1).getCategory());*/
                    String airQualityUrl = "https://devapi.qweather.com/v7/air/now?key=" + key + "&location="+ locationid;
                    sumWeather.setAirQuality(parseJSONWith_airquality(client, airQualityUrl));
                    /*Log.d(TAG, "年间多次" + sumWeather.getAirQuality().getNow());*/
                    weatherData.postValue(sumWeather);
                }).start();
            }
        });
    }
    /*public interface OnSumWeatherRetrievedListener {
        void onSumWeatherRetrieved(sumWeather sumWeather);
    }*/

    private Day parseJSONWith_day(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, responseBody);
                return new Gson().fromJson(responseBody, Day.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取7天天气预报失败", e);
        }
        return null;
    }

    private hour24 parseJSONWith_24(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return new Gson().fromJson(responseBody, hour24.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取24小时天气预报失败", e);
        }
        return null;
    }

    private NowWea parseJSONWith_now(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return new Gson().fromJson(responseBody, NowWea.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前天气失败", e);
        }
        return null;
    }
    private AirNumber parseJSONWith_air(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "哦空间" + responseBody);
                return new Gson().fromJson(responseBody, AirNumber.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前状况失败", e);
        }
        return null;
    }
    private AirQuality parseJSONWith_airquality(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "哦空间" + responseBody);
                return new Gson().fromJson(responseBody, AirQuality.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前状况失败", e);
        }
        return null;
    }
}