package com.example.sunnyweather.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.CityManage;
import com.example.sunnyweather.R;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.ViewModel.CityManageViewModel;
import com.example.sunnyweather.ViewModel.WeatherViewModel;
import com.example.sunnyweather.WeatherViewModelFactory;
import com.example.sunnyweather.bean.AirNumber;
import com.example.sunnyweather.bean.AirQuality;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.NowWea;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.bean.sumWeather;
import com.example.sunnyweather.history.HistoryMessage;
import com.example.sunnyweather.history.HistoryViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private static final String TAG = "MainActivity";
    private CityLocationViewModel cityLocationViewModel;
    private List<CityLocation> cityList;
    private List<HistoryMessage> historyMessages;
    private HistoryViewModel historyViewModel;

    public CityAdapter(CityLocationViewModel cityLocationViewModel, List<CityLocation> cityList, HistoryViewModel historyViewModel) {
        this.cityLocationViewModel = cityLocationViewModel;
        this.cityList = cityList;
        this.historyViewModel = historyViewModel;
    }

    public void setCityList(List<CityLocation> newCityList) {
        cityList = newCityList;
        notifyDataSetChanged();
    }

    public List<String> getAllCityName() {
        List<String> textViewData = new ArrayList<>();
        for (CityLocation cityItem : cityList) {
            textViewData.add(cityItem.cityName);
        }
        return textViewData;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        CityViewHolder holder = new CityViewHolder(view);
        holder.viewsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                CityLocation city = cityList.get(position);
                Toast.makeText(view.getContext(),"可以长按我将我移除哦", Toast.LENGTH_SHORT).show();
            }
        });
        holder.viewsum.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                CityLocation city = cityList.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("提示");
                dialog.setMessage("确定要从常用城市移除" + city.cityName);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity1", "要删除了");
                        cityLocationViewModel.deletecityLocation(city);
                        historyViewModel.deleteHistory(new HistoryMessage(Integer.parseInt(city.nameid)));
                        Log.d("MainActivity1", "要更新界面了");
                        notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                });
                dialog.show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CityLocation cityItem = cityList.get(position);
        holder.tvCityName.setText(cityItem.cityName);
        sumWeather sumWeather = new sumWeather();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HistoryMessage historyMessage = historyViewModel.researcHistoryById(cityItem.nameid);
                Log.e(TAG, "观察到数据更新: " + historyMessage);
                if (historyMessage != null) {
                    if (historyMessage.cityNowAirMessage != null) {
                        sumWeather.setDay(new Gson().fromJson(historyMessage.cityDayAirMessage, Day.class));
                        Log.d(TAG, "解析的日天气数据: " + historyMessage.cityDayAirMessage);
                        sumWeather.setNowWea(new Gson().fromJson(historyMessage.cityNowAirMessage, NowWea.class));
                        sumWeather.setAirnumber(new Gson().fromJson(historyMessage.cityAirAirMessage, AirNumber.class));
                        sumWeather.setHour24(new Gson().fromJson(historyMessage.cityHourAirMessage, hour24.class));
                        sumWeather.setAirQuality(new Gson().fromJson(historyMessage.cityQualityAirMessage, AirQuality.class));
                        Log.e(TAG, "数据更新完成");
                    } else {
                        ;
                    }
                } else {
                    Log.e(TAG, "接收到的数据为 null");
                }
                latch.countDown();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (sumWeather.getDay() != null) {
            holder.cityTemp.setText(sumWeather.getNowWea().getNow().getTemp());
            holder.cityAir.setText(sumWeather.getNowWea().getNow().getText());
            holder.citysit.setText(sumWeather.getAirQuality().getNow().getAqi());
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tvCityName;
        View viewsum;
        TextView cityTemp;
        TextView cityAir;
        TextView citysit;

        public CityViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.City_name);
            viewsum = itemView;
            cityTemp = itemView.findViewById(R.id.City_temp);
            cityAir = itemView.findViewById(R.id.City_air);
            citysit = itemView.findViewById(R.id.City_situation);
        }
    }

    /*public static class CityItem {
        private String cityName;

        public CityItem(String cityName) {
            this.cityName = cityName;
        }

        public String getCityName() {
            return cityName;
        }
    }*/
}
