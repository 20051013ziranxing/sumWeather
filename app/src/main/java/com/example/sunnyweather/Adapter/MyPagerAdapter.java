package com.example.sunnyweather.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.sunnyweather.WeatherHome;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<WeatherHome> mfragmentList;
    private List<String> mTitles;

    public MyPagerAdapter(FragmentManager fm, List<WeatherHome> fragments, List<String> titles) {
        super(fm);
        mfragmentList = fragments;
        mTitles = titles;
    }

    public void updateFragments(List<WeatherHome> fragments, List<String> titles) {
        mfragmentList = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList == null? null:mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList == null? 0:mfragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && position >= 0 && position < mTitles.size()) {
            return mTitles.get(position);
        } else {
            return null;
        }
    }
}
