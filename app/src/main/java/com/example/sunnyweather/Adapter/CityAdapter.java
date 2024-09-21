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

import com.example.sunnyweather.R;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.ViewModel.CityManageViewModel;
import com.example.sunnyweather.ViewModel.WeatherViewModel;
import com.example.sunnyweather.WeatherViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private CityLocationViewModel cityLocationViewModel;
    private List<CityLocation> cityList;

    public CityAdapter(CityLocationViewModel cityLocationViewModel, List<CityLocation> cityList) {
        this.cityLocationViewModel = cityLocationViewModel;
        this.cityList = cityList;
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
        /*WeatherViewModelFactory factory = new WeatherViewModelFactory(cityItem.cityName);
        WeatherViewModel weatherViewModel = new ViewModelProvider((ViewModelStoreOwner) this, factory).get(WeatherViewModel.class);
        weatherViewModel.getWeatherData().observe(lifecycleOwner, weather -> {
            Log.d("MainActivity", "此时天气为oonCreateView");
            holder.cityTemp.setText(weather.getNowWea().getNow().getTemp());
        });*/
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

        public CityViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.City_name);
            viewsum = itemView;
            cityTemp = itemView.findViewById(R.id.City_temp);
            cityAir = itemView.findViewById(R.id.City_air);
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
