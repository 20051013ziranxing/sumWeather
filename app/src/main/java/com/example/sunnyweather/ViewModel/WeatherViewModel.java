package com.example.sunnyweather.ViewModel;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.Location;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.bean.AirNumber;
import com.example.sunnyweather.bean.AirQuality;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.NowWea;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.bean.sumWeather;
import com.example.sunnyweather.history.HistoryMessage;
import com.example.sunnyweather.history.HistoryViewModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "MainActivity";
    /*String key = "ffa53b25cd8947c2a0740c3be0c266f2";*/ //免费
    String key = "ee7aeea04aa2453e81cd83efa3a50b21";
    String key1 = "137d267285e8499dbaa571bfb8049b22";
    private String cityName;
    private String cityNameZHEN;
    private Activity activity;
    private MutableLiveData<sumWeather> weatherData;
    private CityLocationViewModel cityLocationViewModel;
    private HistoryViewModel historyViewModel;

    public WeatherViewModel(String cityName, String cityNameZHEN, CityLocationViewModel cityLocationViewModel
            , HistoryViewModel historyViewModel, Activity activity) {
        Log.d(TAG, "cityName = "+cityName);
        this.cityName = cityName;
        this.cityLocationViewModel = cityLocationViewModel;
        this.activity = activity;
        this.historyViewModel = historyViewModel;
        this.cityNameZHEN = cityNameZHEN;
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
        String locationid = cityName;
        Log.d(TAG, "我得到了" + locationid);
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            sumWeather sumWeather = new sumWeather();
            try {
                String dayUrl = "https://api.qweather.com/v7/weather/7d?key=" + key + "&location="+ locationid;
                String day = parseJSONWith_day(client, dayUrl);
                String hourUrl = "https://api.qweather.com/v7/weather/24h?key=" + key + "&location="+ locationid;
                String hour = parseJSONWith_24(client, hourUrl);
                String nowUrl = "https://api.qweather.com/v7/weather/now?key=" + key + "&location="+ locationid;
                String now = parseJSONWith_now(client, nowUrl);
                /*Log.d(TAG, "年间多次" + sumWeather.getAirnumber().getDaily().get(1).getCategory());*/
                String airQualityUrl = "https://api.qweather.com/v7/air/now?key=" + key + "&location="+ locationid;
                String airQuality = parseJSONWith_airquality(client, airQualityUrl);
                String airUrl = "https://api.qweather.com/v7/indices/1d?type=1,2,3,4,6,9&key=" + key + "&location="+ locationid;
                String air = parseJSONWith_air(client, airUrl);
                if (day != null && hour != null && now != null && airQuality != null && air != null) {
                    Log.e(TAG, "看来都不是空的");
                    sumWeather.setDay(new Gson().fromJson(day, Day.class));
                    sumWeather.setHour24(new Gson().fromJson(hour, hour24.class));
                    sumWeather.setNowWea(new Gson().fromJson(now, NowWea.class));
                    sumWeather.setAirQuality(new Gson().fromJson(airQuality, AirQuality.class));
                    sumWeather.setAirnumber(new Gson().fromJson(air, AirNumber.class));
                    HandlerThread thread = new HandlerThread("DatabaseThread");
                    thread.start();
                    Handler handler = new Handler(thread.getLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "进入了数据库操作啊");
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    HistoryMessage historyMessage = new HistoryMessage(Integer.parseInt(locationid), cityNameZHEN,day,hour, now, airQuality,air);
                                    historyViewModel.updateHistory(historyMessage);
                                }
                            });
                        }
                    });
                } else {
                    Log.e(TAG, "看来都不是空的以容许吧oo");
                    if (activity != null) {
                        Log.e(TAG, "看来都不是空的以容许吧oopp");
                        CountDownLatch latch = new CountDownLatch(1);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HistoryMessage historyMessage = historyViewModel.researcHistoryById(locationid);
                                Log.e(TAG, "观察到数据更新: " + historyMessage);
                                if (historyMessage != null) {
                                    sumWeather.setDay(new Gson().fromJson(historyMessage.cityDayAirMessage, Day.class));
                                    Log.d(TAG, "解析的日天气数据: " + historyMessage.cityDayAirMessage);
                                    sumWeather.setNowWea(new Gson().fromJson(historyMessage.cityNowAirMessage, NowWea.class));
                                    sumWeather.setAirnumber(new Gson().fromJson(historyMessage.cityAirAirMessage, AirNumber.class));
                                    sumWeather.setHour24(new Gson().fromJson(historyMessage.cityHourAirMessage, hour24.class));
                                    sumWeather.setAirQuality(new Gson().fromJson(historyMessage.cityQualityAirMessage, AirQuality.class));
                                    Log.e(TAG, "数据更新完成");
                                } else {
                                    Log.e(TAG, "接收到的数据为 null");
                                }
                                latch.countDown();
                            }
                        }).start();
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Log.e(TAG, "Activity is null");
                    }
                }

            }catch (Exception e) {
            }
            weatherData.postValue(sumWeather);
        }).start();
    }
    /*public interface OnSumWeatherRetrievedListener {
        void onSumWeatherRetrieved(sumWeather sumWeather);
    }*/

    private String parseJSONWith_day(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "请求" + responseBody);
                return responseBody;
                /*return new Gson().fromJson(responseBody, Day.class);*/
            }
        } catch (IOException e) {
            Log.e(TAG, "获取7天天气预报失败", e);
        }
        return null;
    }

    private String parseJSONWith_24(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return responseBody;
                /*return new Gson().fromJson(responseBody, hour24.class);*/
            }
        } catch (IOException e) {
            Log.e(TAG, "获取24小时天气预报失败", e);
        }
        return null;
    }

    private String parseJSONWith_now(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return responseBody;
                /*return new Gson().fromJson(responseBody, NowWea.class);*/
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前天气失败", e);
        }
        return null;
    }
    private String parseJSONWith_air(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "哦空间" + responseBody);
                return responseBody;
                /*return new Gson().fromJson(responseBody, AirNumber.class);*/
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前状况失败", e);
        }
        return null;
    }
    private String parseJSONWith_airquality(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "哦空间" + responseBody);
                return responseBody;
                /*return new Gson().fromJson(responseBody, AirQuality.class);*/
            }
        } catch (IOException e) {
            Log.e(TAG, "获取当前状况失败", e);
        }
        return null;
    }
}