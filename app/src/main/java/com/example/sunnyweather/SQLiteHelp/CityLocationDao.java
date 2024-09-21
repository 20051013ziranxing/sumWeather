package com.example.sunnyweather.SQLiteHelp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CityLocationDao {
    @Insert //增加城市
    void insertCityLocation(CityLocation... CityLocations);
    @Delete //删除城市
    void deleteCityLocation(CityLocation... CityLocations);
    /*@Query("DELETE FROM cityLocationMessage") //清空城市
    void deleteAllAtudent();*/
    @Query("SELECT * FROM cityLocationMessage") //获取所有的城市
    LiveData<List<CityLocation>> getAllCityLocationLive();
    @Query("SELECT * FROM cityLocationMessage WHERE cityAttributeMessage = :nameAttribute")
    CityLocation getCityLocationByName(String nameAttribute);
    @Query("SELECT * FROM cityLocationMessage WHERE cityNameMessage = :nameAttribute")
    CityLocation getCityLocationByNamename(String nameAttribute);
}
