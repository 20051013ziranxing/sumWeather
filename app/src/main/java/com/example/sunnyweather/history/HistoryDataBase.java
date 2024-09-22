package com.example.sunnyweather.history;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sunnyweather.SQLiteHelp.CityLocationDao;

@Database(entities = {HistoryMessage.class}, version = 3, exportSchema = false)
public abstract class HistoryDataBase extends RoomDatabase {
    private static HistoryDataBase mInstance;
    private static final String DATABASE_NAME = "my_db.db1";
    public static synchronized HistoryDataBase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), HistoryDataBase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration() //对数据库进行更新的时候，采用直接舍弃所有的数据
                    .build();
        }
        return mInstance;
    }
    public abstract HistoryDao getHistoryDao();
}
