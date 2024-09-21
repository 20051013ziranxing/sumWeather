package com.example.sunnyweather.bean;

public class sumWeather {
    private Day day;
    private hour24 hour24;
    private NowWea nowWea;
    private AirNumber airnumber;
    private AirQuality airQuality;
    public sumWeather() {
    }
    public sumWeather(NowWea nowWea, com.example.sunnyweather.bean.hour24 hour24, Day day, AirNumber airnumber, AirQuality airQuality) {
        this.day = day;
        this.hour24 = hour24;
        this.nowWea = nowWea;
        this.airnumber = airnumber;
        this.airQuality = airQuality;
    }
    public Day getDay() {
        return day;
    }
    public void setDay(Day day) {
        this.day = day;
    }
    public com.example.sunnyweather.bean.hour24 getHour24() {
        return hour24;
    }
    public void setHour24(com.example.sunnyweather.bean.hour24 hour24) {
        this.hour24 = hour24;
    }
    public NowWea getNowWea() {
        return nowWea;
    }
    public void setNowWea(NowWea nowWea) {
        this.nowWea = nowWea;
    }

    public AirNumber getAirnumber() {
        return airnumber;
    }
    public void setAirnumber(AirNumber airnumber) {
        this.airnumber = airnumber;
    }
    public AirQuality getAirQuality() {
        return airQuality;
    }
    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
    }
}
