package com.example.sunnyweather.bean;

import com.example.sunnyweather.R;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;


public class hour24 {
    private String code;
    private String updateTime;
    private String fxLink;
    private ReferBean refer;
    private List<HourlyBean> hourly;

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

    public List<HourlyBean> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyBean> hourly) {
        this.hourly = hourly;
    }

    public static class ReferBean {
        private List<String> sources;
        private List<String> license;
    }

    public static class HourlyBean {

        private String fxTime;
        private String temp;
        private String icon;
        private String text;
        private String wind360;
        private String windDir;
        private String windScale;
        private String windSpeed;
        private String humidity;
        private String pop;
        private String precip;
        private String pressure;
        private String cloud;
        private String dew;

        public String getFxTime() {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(fxTime);
            LocalTime time = zonedDateTime.toLocalTime();
            return time.toString();
        }

        public void setFxTime(String fxTime) {
            this.fxTime = fxTime;
        }

        public String getTemp() {
            return temp + "°";
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getText() {
            return text;
        }
        /*public int getWeatherImageResId() {
            String string = text;
            if (string.equals("晴")) {
                return R.drawable.tianqi_qing;
            } else if (string.equals("暴雨")) {
                return R.drawable.tianqi_baoyu;
            } else if (string.equals("冰雹")) {
                return R.drawable.tianqi_bingbao;
            } else if (string.equals("大雪")) {
                return R.drawable.tianqi_daxue;
            } else if (string.equals("大雨")) {
                return R.drawable.tianqi_dayu;
            } else if (string.equals("多云")) {
                return R.drawable.tianqi_duoyun;
            } else if (string.equals("雷雨")) {
                return R.drawable.tianqi_leiyu;
            } else if (string.equals("小雨")) {
                return R.drawable.tianqi_xiaoyu;
            } else if (string.equals("小雪")) {
                return R.drawable.tianqi_xiaoxue;
            } else if (string.equals("中雨")) {
                return R.drawable.tianqi_zhongyu;
            } else if (string.equals("中雪")) {
                return R.drawable.tianqi_zhongxue;
            } else if (string.equals("阴")) {
                return R.drawable.tianqi_yintian;
            } else if (string.equals("雨夹雪")) {
                return R.drawable.tianqi_yujiaxue;
            }
            return R.drawable.tianqi_xuezhuanqing;
        }*/

        public void setText(String text) {
            this.text = text;
        }
    }
}
