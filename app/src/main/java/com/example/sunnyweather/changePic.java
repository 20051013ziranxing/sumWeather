package com.example.sunnyweather;

public class changePic {
    public static int getchange(String string) {
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
        } else if (string.equals("雷阵雨")) {
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
        } else if (string.equals("沙尘暴")) {
            return R.drawable.tianqi_shachen;
        } else if (string.equals("霾")) {
            return R.drawable.tianqi_wumai;
        }
        return R.drawable.tianqi_xuezhuanqing;
    }
}
