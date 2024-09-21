package com.example.sunnyweather.bean;

import androidx.room.Entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Day {
    public String code;
    public String updateTime;
    public String fxLink;
    public List<Daily> daily;
    public Refer refer;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public class Daily {
        public String fxDate;
        public String sunrise;
        public String sunset;
        public String moonrise;
        public String moonset;
        public String moonPhase;
        public String moonPhaseIcon;
        public String tempMax;
        public String tempMin;
        public String iconDay;
        public String textDay;

        public String getTextDay() {
            return textDay;
        }

        public void setTextDay(String textDay) {
            this.textDay = textDay;
        }

        public String iconNight;
        public String textNight;
        public String wind360Day;
        private String windDirDay;
        private String windScaleDay;
        private String windSpeedDay;
        private String wind360Night;
        private String windDirNight;
        private String windScaleNight;

        public String getFxDate() {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("M/dd");
            Date date = null;
            try {
                date = inputFormat.parse(fxDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        }

        public void setFxDate(String fxDate) {
            this.fxDate = fxDate;
        }

        public String getTempMax() {
            return tempMax;
        }

        public void setTempMax(String tempMax) {
            this.tempMax = tempMax;
        }

        public String getTempMin() {
            return tempMin + "° / " + tempMax + "°";
        }

        public void setTempMin(String tempMin) {
            this.tempMin = tempMin;
        }

        public String getIconDay() {
            return iconDay;
        }

        public void setIconDay(String iconDay) {
            this.iconDay = iconDay;
        }

        public String getIconNight() {
            return iconNight;
        }

        public void setIconNight(String iconNight) {
            this.iconNight = iconNight;
        }

        private String windSpeedNight;
        private String humidity;
        private String precip;
        private String pressure;
        private String vis;
        private String cloud;
        private String uvIndex;


    }

    public class Refer {
        private List<String> sources;
        private List<String> license;
    }
}
