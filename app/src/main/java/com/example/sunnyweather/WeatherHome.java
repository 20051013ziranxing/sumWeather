package com.example.sunnyweather;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunnyweather.Adapter.adapter24h;
import com.example.sunnyweather.Adapter.adapter7day;
import com.example.sunnyweather.ViewModel.WeatherViewModel;
import com.example.sunnyweather.bean.AirNumber;
import com.example.sunnyweather.bean.Day;
import com.example.sunnyweather.bean.hour24;
import com.example.sunnyweather.bean.sumWeather;
import com.example.sunnyweather.databinding.FragmentWeatherHomeBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherHome extends Fragment {
    private WeatherViewModel weatherViewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public WeatherHome() {

    }

    public WeatherHome(String city) {
        mParam1 = city;
    }
    public static WeatherHome newInstance(String param1, String param2) {
        WeatherHome fragment = new WeatherHome(param1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static WeatherHome newInstance(String cityName) {
        WeatherHome fragment = new WeatherHome();
        Bundle args = new Bundle();
        args.putString("CITY_NAME", cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            String cityName = getArguments().getString("CITY_NAME");
            WeatherViewModelFactory factory = new WeatherViewModelFactory(cityName);
            weatherViewModel = new ViewModelProvider(this, factory).get(WeatherViewModel.class);
            weatherViewModel.loadWeatherData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_home, container, false);
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weather -> {
            Log.d("MainActivity", "此时天气为oonCreateView");
            updateWeatherView(view, weather);
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void updateWeatherView(View view, sumWeather weather) {
        TextView mainTemp = view.findViewById(R.id.mainTemp);
        if (weather.getNowWea().getNow().getTemp() == null) {
            mainTemp.setText(0);
        } else {
            mainTemp.setText(weather.getNowWea().getNow().getTemp());
        }
        Log.d("MainActivity", weather.getNowWea().getNow().getTemp());
        TextView mainNow = view.findViewById(R.id.mainNow);
        mainNow.setText(weather.getNowWea().getNow().getText() + " " + weather.getDay().getDaily().get(0).getTempMin());

        FragmentWeatherHomeBinding fragmentWeatherHomeBinding = DataBindingUtil.bind(view);
        List<hour24.HourlyBean> hourlyBean = weather.getHour24().getHourly();
        RecyclerView mainTemp24h = fragmentWeatherHomeBinding.mainTemp24h;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mainTemp24h.setLayoutManager(layoutManager);
        adapter24h adapter24h = new adapter24h(hourlyBean);
        mainTemp24h.setAdapter(adapter24h);

        List<Day.Daily> dailies = weather.getDay().getDaily();
        RecyclerView maintemp7day = fragmentWeatherHomeBinding.mainDat7;
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        maintemp7day.setLayoutManager(layoutManager1);
        adapter7day adapter7day = new adapter7day(dailies);
        maintemp7day.setAdapter(adapter7day);

        fragmentWeatherHomeBinding.setAir(weather.getAirnumber());
        fragmentWeatherHomeBinding.setAirhh(weather.getAirQuality().getNow());
        view.invalidate();
    }
}