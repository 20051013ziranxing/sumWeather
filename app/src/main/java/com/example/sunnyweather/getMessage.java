package com.example.sunnyweather;

import android.util.Log;

import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.LicationData;
import com.example.sunnyweather.bean.NowWea;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.bean.sumWeather;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getMessage {
    /*private static final String TAG = "MainActivity";
    private String cityName;
    public getMessage(String cityName) {
        this.cityName = cityName;
    }
    public void getsumWeatherMessage(final OnSumWeatherRetrievedListener listener) {
        Location location = new Location();
        location.Locationoo(cityName, new Location.OnLocationRetrievedListener() {
            @Override
            public void onLocationRetrieved(String locationid) {
                Log.d(TAG, "我得到了" + locationid);
                new Thread(() -> {
                    OkHttpClient client = new OkHttpClient();
                    sumWeather sumWeather = new sumWeather();
                    String dayUrl = "https://devapi.qweather.com/v7/weather/7d?key=ffa53b25cd8947c2a0740c3be0c266f2&location=" + locationid;
                    sumWeather.setDay(parseJSONWith_day(client, dayUrl));
                    String hourUrl = "https://devapi.qweather.com/v7/weather/24h?key=ffa53b25cd8947c2a0740c3be0c266f2&location=" + locationid;
                    sumWeather.setHour24(parseJSONWith_24(client, hourUrl));
                    String nowUrl = "https://devapi.qweather.com/v7/weather/now?key=ffa53b25cd8947c2a0740c3be0c266f2&location=" + locationid;
                    sumWeather.setNowWea(parseJSONWith_now(client, nowUrl));
                    Log.d(TAG, sumWeather.getNowWea().getNow().getTemp());
                }).start();
            }
        });
    }
    public interface OnSumWeatherRetrievedListener {
        void onSumWeatherRetrieved(sumWeather sumWeather);
    }

    private Day parseJSONWith_day(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            Log.e(TAG, response.body().string());
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.e(TAG, responseBody);
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
                Log.e(TAG, responseBody);
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
                Log.e(TAG, responseBody);
                return new Gson().fromJson(responseBody, NowWea.class);
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前天气失败", e);
        }
        return null;
    }*/
}