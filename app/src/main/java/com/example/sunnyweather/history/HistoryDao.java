package com.example.sunnyweather.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sunnyweather.SQLiteHelp.CityLocation;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert //增加城市信息
    void insertHistory(HistoryMessage... historyMessages);
    @Delete //删除城市信息
    void deleteHistory(HistoryMessage... historyMessages);
    @Query("SELECT * FROM historymessageall") //获取所有的城市信息，用于没网的时候使用
    LiveData<List<HistoryMessage>> getAllHistoryMessageLive();
    @Query("SELECT * FROM historymessageall WHERE cityIdMessage = :cityIdMessage")
    //根据城市的id查找城市，存在的话就将里面信息进行修改
    HistoryMessage getHistoryMessageById(String cityIdMessage);
    @Update
    void updateHistory(HistoryMessage... historyMessages);
}
