package com.example.sunnyweather;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;
import com.example.sunnyweather.ViewModel.CityManageViewModel;

public class WeatherApp extends Application {
    private static final String TAG = "MainActivity";
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // 定位隐私政策同意
        AMapLocationClient.updatePrivacyShow(mContext,true,true);
        AMapLocationClient.updatePrivacyAgree(mContext,true);
        // 地图隐私政策同意
        MapsInitializer.updatePrivacyShow(mContext,true,true);
        MapsInitializer.updatePrivacyAgree(mContext,true);
        // 搜索隐私政策同意
        ServiceSettings.updatePrivacyShow(mContext,true,true);
        ServiceSettings.updatePrivacyAgree(mContext,true);
    }
    public static Context getContext() {
        return mContext;
    }
}
