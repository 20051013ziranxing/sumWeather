package com.example.sunnyweather.SQLiteHelp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class CityLocationRepository {
    private CityLocationDao cityLocationDao;
    public CityLocationRepository(Context context) {
        MyDataBase dataBase = MyDataBase.getInstance(context);
        this.cityLocationDao = dataBase.getCityLocationDao();
    }
    //对数据进行添加
    public void insertCityLocation(CityLocation... cityLocations) {
        new InsertCityLocationTask(cityLocationDao).execute(cityLocations);
    }
    class InsertCityLocationTask extends AsyncTask<CityLocation, Void, Void> {
        private CityLocationDao cityLocationDao;
        public InsertCityLocationTask(CityLocationDao cityLocationDao) {
            this.cityLocationDao = cityLocationDao;
        }
        @Override
        protected Void doInBackground(CityLocation... students) {
            cityLocationDao.insertCityLocation(students);
            return null;
        }
    }
    //对数据进行删除
    public void deleteCityLocation(CityLocation... cityLocations) {
        Log.d("MainActivity1", "没想到吧，我执行到了Repository");
        new DeleteCityLocationTask(cityLocationDao).execute(cityLocations);
    }
    class DeleteCityLocationTask extends AsyncTask<CityLocation, Void, Void> {
        private CityLocationDao cityLocationDao;
        public DeleteCityLocationTask(CityLocationDao cityLocationDao) {
            this.cityLocationDao = cityLocationDao;
        }
        @Override
        protected Void doInBackground(CityLocation... cityLocations) {
            cityLocationDao.deleteCityLocation(cityLocations);
            return null;
        }
    }
    //对数据进行查找
    public LiveData<List<CityLocation>> getAllCityLocationLive() {
        return cityLocationDao.getAllCityLocationLive();
    }
    /*public CityLocation getCityLocationBycityName(String cityname) {
        return cityLocationDao.getCityLocationByName(cityname);
    }*/
    public LiveData<CityLocation> getCityLocationByName(String cityName) {
        return new MutableLiveData<CityLocation>() {
            @Override
            protected void onActive() {
                super.onActive();
                loadCityLocationByName(cityName);
            }

            private void loadCityLocationByName(String cityName) {
                new Thread(() -> {
                    CityLocation cityLocation = cityLocationDao.getCityLocationByName(cityName);
                    postValue(cityLocation);
                }).start();
            }
        };
    }
    public LiveData<CityLocation> getCityLocationByNamename(String cityName) {
        return new MutableLiveData<CityLocation>() {
            @Override
            protected void onActive() {
                super.onActive();
                loadCityLocationByName(cityName);
            }

            private void loadCityLocationByName(String cityName) {
                new Thread(() -> {
                    CityLocation cityLocation = cityLocationDao.getCityLocationByNamename(cityName);
                    postValue(cityLocation);
                }).start();
            }
        };
    }
}
