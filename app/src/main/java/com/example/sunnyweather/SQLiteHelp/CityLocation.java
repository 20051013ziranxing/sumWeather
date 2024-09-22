package com.example.sunnyweather.SQLiteHelp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cityLocationMessage")
public class CityLocation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;
    @ColumnInfo(name = "cityAttributeMessage", typeAffinity = ColumnInfo.TEXT)
    public String nameAttribute;
    @ColumnInfo(name = "cityidMessage", typeAffinity = ColumnInfo.TEXT)
    public String nameid;
    @ColumnInfo(name = "cityNameMessage", typeAffinity = ColumnInfo.TEXT)
    public String cityName;
    @ColumnInfo(name = "cityDayAir", typeAffinity = ColumnInfo.TEXT)
    public String cityDayAirMessage;
    @ColumnInfo(name = "cityHourAir", typeAffinity = ColumnInfo.TEXT)
    public String cityHourAirMessage;
    @ColumnInfo(name = "cityNowAir", typeAffinity = ColumnInfo.TEXT)
    public String cityNowAirMessage;
    @ColumnInfo(name = "cityQualityAir", typeAffinity = ColumnInfo.TEXT)
    public String cityQualityAirMessage;
    @ColumnInfo(name = "cityAirAir", typeAffinity = ColumnInfo.TEXT)
    public String cityAirAirMessage;

    public CityLocation(String nameAttribute, String cityName, String nameid) {
        this.nameAttribute = nameAttribute;
        this.cityName = cityName;
        this.nameid = nameid;
        this.id = id;
        /*this.cityAirAirMessage = null;*/
    }
    @Ignore
    public CityLocation(int id,String cityName, String nameAttribute, String nameid, String cityDayAirMessage, String cityHourAirMessage, String cityNowAirMessage, String cityQualityAirMessage, String cityAirAirMessage) {
        this.id = id;
        this.cityName = cityName;
        this.nameAttribute = nameAttribute;
        this.nameid = nameid;
        this.cityDayAirMessage = cityDayAirMessage;
        this.cityHourAirMessage = cityHourAirMessage;
        this.cityNowAirMessage = cityNowAirMessage;
        this.cityQualityAirMessage = cityQualityAirMessage;
        this.cityAirAirMessage = cityAirAirMessage;
    }
    @Ignore //告诉Room不要用这个构造方法，这是我们所用的方法
    public CityLocation(String cityName) {
        this.cityName = cityName;
    }
}
