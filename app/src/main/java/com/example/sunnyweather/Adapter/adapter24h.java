package com.example.sunnyweather.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.databinding.H24ItemBinding;

import java.util.List;

public class adapter24h extends RecyclerView.Adapter<adapter24h.MyViewHolder> {
    private List<hour24.HourlyBean> hourlyList;
    public adapter24h(List<hour24.HourlyBean> list) {
        this.hourlyList = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        H24ItemBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.h24_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter24h.MyViewHolder holder, int position) {
        hour24.HourlyBean hourlyBean = hourlyList.get(position);
        holder.h24ItemBinding.setHourly(hourlyBean);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private H24ItemBinding h24ItemBinding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public MyViewHolder(H24ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.h24ItemBinding = itemBinding;
        }
    }
}
