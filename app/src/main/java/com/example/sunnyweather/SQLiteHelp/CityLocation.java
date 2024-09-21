package com.example.sunnyweather.SQLiteHelp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cityLocationMessage")
public class CityLocation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    int id;
    @ColumnInfo(name = "cityAttributeMessage", typeAffinity = ColumnInfo.TEXT)
    public String nameAttribute;
    @ColumnInfo(name = "cityidMessage", typeAffinity = ColumnInfo.TEXT)
    public String nameid;
    @ColumnInfo(name = "cityNameMessage", typeAffinity = ColumnInfo.TEXT)
    public String cityName;

    public CityLocation(String nameAttribute, String cityName, String nameid) {
        this.nameAttribute = nameAttribute;
        this.cityName = cityName;
        this.nameid = nameid;
        this.id = id;
    }
    @Ignore //告诉Room不要用这个构造方法，这是我们所用的方法
    public CityLocation(String cityName) {
        this.cityName = cityName;
    }
}
