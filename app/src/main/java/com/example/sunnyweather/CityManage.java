package com.example.sunnyweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.sunnyweather.Adapter.CityAdapter;
import com.example.sunnyweather.Adapter.searchcityAdapter;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.ViewModel.CityManageViewModel;
import com.example.sunnyweather.ViewModel.WeatherViewModel;
import com.example.sunnyweather.ViewModel.searchCityViewModel;
import com.example.sunnyweather.bean.searchCity;
import com.example.sunnyweather.bean.sumWeather;
import com.example.sunnyweather.databinding.ActivityCityManageBinding;
import com.example.sunnyweather.databinding.FragmentSearchCityBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityManage extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Location locationService;
    CityLocationViewModel cityLocationViewModel;
    private String[] data = {"定位","北京","上海","广州","深圳","杭州","成都","武汉","重庆","天津","苏州","西安","安康","渭南","南京","榆林","南京","长沙","郑州","青岛"
            ,"东莞","昆明","宁波","合肥"};
    Toolbar toolbar_City;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_city_manage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityLocationViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CityLocationViewModel.class);
        toolbar_City = findViewById(R.id.toolbar_CityManage);
        setSupportActionBar(toolbar_City);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img);
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHHH, new FragmentCitySearchMOhu(cityLocationViewModel)).commit();
        /*//用户选择的城市数据列表
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CityManage.this,
                android.R.layout.simple_list_item_1,data);
        ListView listView = findViewById(R.id.recyclerchoice1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = data[position];
                if (selectedCity.equals("定位")) {
                    cityLocationViewModel.researchhh("定位").observe((LifecycleOwner) view.getContext(), cityLocation -> {
                        if (cityLocation == null) {
                            setupLocationService();
                        } else {
                            Toast.makeText(CityManage.this, "这个城市添加过了", Toast.LENGTH_SHORT).show();
                        }
                        ((Activity) view.getContext()).runOnUiThread(() -> mDrawerlayout.closeDrawer(GravityCompat.END));
                    });
                } else {
                    cityLocationViewModel.researchhh(selectedCity).observe((LifecycleOwner) view.getContext(), cityLocation -> {
                        if (cityLocation == null) {
                            cityLocationViewModel.insertcityLocation(new CityLocation(selectedCity, selectedCity, selectedCity));
                        } else {
                            Toast.makeText(CityManage.this, "这个城市添加过了", Toast.LENGTH_SHORT).show();
                        }
                        ((Activity) view.getContext()).runOnUiThread(() -> mDrawerlayout.closeDrawer(GravityCompat.END));
                    });
                    }
                }
        });*/
        /*//悬浮按钮的点击事件
        FloatingActionButton floatingActionButton = findViewById(R.id.folatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerlayout.openDrawer(GravityCompat.END);
            }
        });
        //常用城市界面*/
        /*RecyclerView recyclerView = findViewById(R.id.recyclerCityUse);
        List<CityLocation> cityLocations = new ArrayList<>();
        cityAdapter= new CityAdapter(cityLocationViewModel,cityLocations);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityLocationViewModel.research().observe(this, new Observer<List<CityLocation>>() {
            @Override
            public void onChanged(List<CityLocation> cityLocations) {
                cityAdapter.setCityList(cityLocations);
                cityAdapter.notifyDataSetChanged();
            }
        });*/
    }

    //为标题的按钮注册点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.writeadd) {
            /*fragmentManager = getSupportFragmentManager();*/
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentHHH, new searchCityFragment(cityLocationViewModel)).commit();
        } else {
            finish();
        }
        return true;
    }

    //为标题设置编辑选项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write, menu);
        return true;
    }

    public void Fragmentchange(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHHH, fragment).commit();
    }
}