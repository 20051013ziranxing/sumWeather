package com.example.sunnyweather.history;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "HistoryMessageAll")
public class HistoryMessage {
    /*@ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;*/
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cityIdMessage", typeAffinity = ColumnInfo.INTEGER)
    public int cityId;
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

    public HistoryMessage(int cityId, String cityName, String cityDayAirMessage,
                          String cityHourAirMessage, String cityNowAirMessage,
                          String cityQualityAirMessage, String cityAirAirMessage) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityDayAirMessage = cityDayAirMessage;
        this.cityHourAirMessage = cityHourAirMessage;
        this.cityNowAirMessage = cityNowAirMessage;
        this.cityQualityAirMessage = cityQualityAirMessage;
        this.cityAirAirMessage = cityAirAirMessage;
    }

    @Ignore
    public HistoryMessage(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
    @Ignore
    public HistoryMessage(int cityId) {
        this.cityId = cityId;
    }
}
