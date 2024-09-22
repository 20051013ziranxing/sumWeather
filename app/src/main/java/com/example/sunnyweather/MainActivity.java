package com.example.sunnyweather;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.sunnyweather.Adapter.MyPagerAdapter;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.ViewModel.MyViewModel;
import com.example.sunnyweather.databinding.ActivityMainBinding;
import com.example.sunnyweather.history.HistoryMessage;
import com.example.sunnyweather.history.HistoryViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private MyViewModel cityManageViewModel;
    Toolbar toolbar_main;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    TabLayout tabLayout;
    CityLocationViewModel cityLocationViewModel;
    HistoryViewModel historyViewModel;
    private static final String TAG = "MainActivity1";
    private ActivityMainBinding binding;
    private ActivityResultLauncher<String> requestPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*setContentView(R.layout.activity_main);*/
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_2);
        }
        /*initLocation();*/
        requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            Log.d(TAG, "权限申请结果: " + result);
        });

        /*startLocation();*/
        cityManageViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewPager = findViewById(R.id.vp);
        tabLayout = findViewById(R.id.table_layout);
        cityManageViewModel.getFragments().observe(this, new Observer<List<WeatherHome>>() {
            @Override
            public void onChanged(List<WeatherHome> fragments) {
                Log.d(TAG, "我变化了标题");
                updateUI(cityManageViewModel.getFragments().getValue(), cityManageViewModel.getTitles().getValue());
            }
        });

        cityLocationViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(CityLocationViewModel.class);
        historyViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(HistoryViewModel.class);

    }
    private void updateUI(List<WeatherHome> fragments, List<String> titles) {
        if (adapter == null) {
            adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, titles);
            viewPager.setAdapter(adapter);
            setupTabLayout(tabLayout, adapter);
        } else {
            adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, titles);
            /*adapter.updateFragments(fragments, titles);*/
            viewPager.setAdapter(adapter);
            Log.d(TAG, fragments.size() + " " + titles.size());
            /*adapter.notifyDataSetChanged();*/
            tabLayout.removeAllTabs();
            for (int i = 0; i < titles.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
            }
            setupTabLayout(tabLayout, adapter);
        }
    }
    private void setupTabLayout(TabLayout tabLayout, MyPagerAdapter adapter) {
        tabLayout.removeAllTabs();
        for (int i = 0; i < adapter.getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(i)));
        }
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CityManage.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onResume: 已获取到权限");
        } else {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        cityLocationViewModel.research().observe(this, new Observer<List<CityLocation>>() {
            @Override
            public void onChanged(List<CityLocation> cityLocations) {
                List<WeatherHome> fragments = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (CityLocation cityLocation : cityLocations) {
                    WeatherHome weatherHome = WeatherHome.newInstance(cityLocation.nameid,cityLocation.cityName
                            , cityLocationViewModel, historyViewModel);
                    fragments.add(weatherHome);
                    Log.d(TAG, "添加了" + cityLocation.cityName);
                    titles.add(cityLocation.cityName);
                }
                cityManageViewModel.refreshFragment(fragments, titles);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}