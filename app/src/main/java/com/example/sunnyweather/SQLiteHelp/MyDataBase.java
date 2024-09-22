package com.example.sunnyweather.SQLiteHelp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CityLocation.class}, version = 4, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase mInstance;
    private static final String DATABASE_NAME = "my_db.db";
    public static synchronized MyDataBase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration() //对数据库进行更新的时候，采用直接舍弃所有的数据
                    .build();
        }
        return mInstance;
    }
    public abstract CityLocationDao getCityLocationDao(); //Room会帮我们自动实现
}
