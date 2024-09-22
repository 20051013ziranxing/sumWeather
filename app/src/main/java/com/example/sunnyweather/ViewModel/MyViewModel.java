package com.example.sunnyweather.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.WeatherHome;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {
    private static final String TAG = "MainActivity";

    private MutableLiveData<List<WeatherHome>> mFragments = new MutableLiveData<>();
    private MutableLiveData<List<String>> mTitles = new MutableLiveData<>();
    public MyViewModel() {
        /*List<WeatherHome> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        WeatherHome weatherHome = WeatherHome.newInstance("北京");
        fragments.add(weatherHome);
        titles.add("北京");
        mTitles.setValue(titles);
        mFragments.setValue(fragments);*/
    }
    /*public void addFragment(WeatherHome fragment, String title) {
        List<WeatherHome> fragments = new ArrayList<>(mFragments.getValue());
        fragments.add(fragment);
        mFragments.setValue(fragments);
        List<String> titles = new ArrayList<>(mTitles.getValue());
        titles.add(title);
        mTitles.setValue(titles);
    }
    public void removeFragment(int position) {
        List<WeatherHome> fragments = new ArrayList<>(mFragments.getValue());
        fragments.remove(position);
        mFragments.setValue(fragments);
        List<String> titles = new ArrayList<>(mTitles.getValue());
        titles.remove(position);
        mTitles.setValue(titles);
    }*/
    public void refreshFragment(List<WeatherHome> fragments, List<String> titles) {
        mTitles.setValue(titles);
        mFragments.setValue(fragments);
    }

    public LiveData<List<WeatherHome>> getFragments() {
        return mFragments;
    }

    public LiveData<List<String>> getTitles() {
        return mTitles;
    }
}
