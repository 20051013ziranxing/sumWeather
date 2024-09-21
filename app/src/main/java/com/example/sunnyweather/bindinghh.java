package com.example.sunnyweather;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class bindinghh {
    @BindingAdapter("weatherImage")
    public static void setWeatherImage(ImageView imageView, String weatherText) {
        int resId = changePic.getchange(weatherText);
        imageView.setImageResource(resId);
    }
}
