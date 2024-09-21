package com.example.sunnyweather.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.databinding.Day7ItemBinding;
import com.example.sunnyweather.databinding.H24ItemBinding;

import java.util.List;

public class adapter7day extends RecyclerView.Adapter<adapter7day.MyViewHolder> {
    private List<Day.Daily> dailies;

    public adapter7day(List<Day.Daily> dailies) {
        this.dailies = dailies;
    }

    @NonNull
    @Override
    public adapter7day.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Day7ItemBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.day7_item, parent, false);
        return new adapter7day.MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter7day.MyViewHolder holder, int position) {
        Day.Daily daily = dailies.get(position);
        holder.day7ItemBinding.setDadly(daily);
    }

    @Override
    public int getItemCount() {
        return dailies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Day7ItemBinding day7ItemBinding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public MyViewHolder(Day7ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.day7ItemBinding = itemBinding;
        }
    }
}
