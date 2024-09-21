package com.example.sunnyweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.sunnyweather.bean.LicationData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Location {
    private static final String TAG = "MainActivity";
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationListener locationListener;
    private Context context;
    public Location() {

    }
    public Location(Context context) throws Exception {
        this.context = context;
        initLocationClient();
    }

    // 初始化定位客户端和配置选项
    private void initLocationClient() throws Exception {
        mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(6000);
        mLocationClient.setLocationOption(mLocationOption);
    }

    // 设置定位监听器
    public void setLocationListener(AMapLocationListener listener) {
        this.locationListener = listener;
        mLocationClient.setLocationListener(listener);
    }

    // 开始定位
    public void startLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationClient.startLocation();
        } else {
            ;
        }
    }

    // 停止定位
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    // 请求定位权限
    public void requestLocationPermission(ActivityResultLauncher<String> requestPermissionLauncher) {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void Locationoo(String city, final OnLocationRetrievedListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://geoapi.qweather.com/v2/city/lookup?key=ffa53b25cd8947c2a0740c3be0c266f2&number=1&location=" + city)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "请求失败：" + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    LicationData weatherResponse = gson.fromJson(responseData, LicationData.class);
                    if ("200".equals(weatherResponse.getCode())) {
                        List<LicationData.Location> location = weatherResponse.getLocation();
                        String rte = "";
                        if (location != null) {
                            for (LicationData.Location location1 : location) {
                                rte += location1.getId();
                                Log.d(TAG, "ret + " + rte);
                            }
                            listener.onLocationRetrieved(rte);
                        } else {
                            Log.d(TAG, "location=null");
                        }
                    } else {
                        Log.d(TAG, "解析错误：" + weatherResponse.getCode());
                    }
                } else {
                    Log.d(TAG, "请求不成功：" + response.code());
                }
            }
        });
    }

    public interface OnLocationRetrievedListener {
        void onLocationRetrieved(String locationId);
    }
}
