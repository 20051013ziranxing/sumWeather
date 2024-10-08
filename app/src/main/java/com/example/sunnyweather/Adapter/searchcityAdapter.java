package com.example.sunnyweather.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.CityManage;
import com.example.sunnyweather.FragmentCitySearchMOhu;
import com.example.sunnyweather.R;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.bean.AirNumber;
import com.example.sunnyweather.bean.AirQuality;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.NowWea;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.bean.searchCity;
import com.example.sunnyweather.bean.sumWeather;
import com.example.sunnyweather.databinding.H24ItemBinding;
import com.example.sunnyweather.databinding.SearchcityItemBinding;
import com.example.sunnyweather.history.HistoryMessage;
import com.example.sunnyweather.history.HistoryViewModel;
import com.example.sunnyweather.searchCityFragment;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;

public class searchcityAdapter extends RecyclerView.Adapter<searchcityAdapter.cityViewHolder> {
    private List<searchCity.Location> cityList;
    private CityLocationViewModel cityLocationViewModel;
    private CityManage cityManage;
    private HistoryViewModel historyViewModel;
    public searchcityAdapter(List<searchCity.Location> location, CityLocationViewModel cityLocationViewModel,
                             HistoryViewModel historyViewModel, CityManage cityManage) {
        this.cityList = location;
        this.cityLocationViewModel = cityLocationViewModel;
        this.cityManage = cityManage;
        this.historyViewModel = historyViewModel;
    }

    public static class cityViewHolder extends RecyclerView.ViewHolder {
        private SearchcityItemBinding searchcityItemBinding;
        View view;
        public cityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public cityViewHolder(SearchcityItemBinding searchcityItemBinding) {
            super(searchcityItemBinding.getRoot());
            this.searchcityItemBinding = searchcityItemBinding;
            view = searchcityItemBinding.getRoot();
        }
    }
    @NonNull
    @Override
    public searchcityAdapter.cityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchcityItemBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.searchcity_item, parent, false);
        searchcityAdapter.cityViewHolder cityViewHolder = new searchcityAdapter.cityViewHolder(inflate);
        if (cityViewHolder.view != null) {
            Log.d("MainActivity", "不是空的！！！");
            cityViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = cityViewHolder.getAdapterPosition();
                    searchCity.Location location = cityList.get(position);
                    Log.d("MainActivity", "执行到了获取当前对象！！！");
                    CountDownLatch latch = new CountDownLatch(1);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HistoryMessage historyMessage = historyViewModel.researcHistoryById(location.getId());
                            Log.e("MainActivity", "观察到数据更新: " + historyMessage);
                            if (historyMessage == null) {
                                cityLocationViewModel.insertcityLocation(new CityLocation(location.getId(), location.getName(), location.getId()));
                                historyViewModel.insertHistory(new HistoryMessage(Integer.parseInt(location.getId()), location.getName()));
                            } else {
                                /*Toast.makeText(parent.getContext(), "这个城市添加过了", Toast.LENGTH_SHORT).show();*/
                            }
                            latch.countDown();
                        }
                    }).start();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    cityManage.Fragmentchange(new FragmentCitySearchMOhu(cityLocationViewModel, historyViewModel));
                }
            });
        } else {
            Log.d("MainActivity", "我竟然是空的！！！");
        }
        return cityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull searchcityAdapter.cityViewHolder holder, int position) {
        searchCity.Location location = cityList.get(position);
        holder.searchcityItemBinding.setLocation(location);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}
