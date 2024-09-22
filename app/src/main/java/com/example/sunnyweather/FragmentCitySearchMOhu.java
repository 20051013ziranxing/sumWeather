package com.example.sunnyweather;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.sunnyweather.Adapter.CityAdapter;
import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.bean.sumWeather;
import com.example.sunnyweather.history.HistoryMessage;
import com.example.sunnyweather.history.HistoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCitySearchMOhu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCitySearchMOhu extends Fragment {
    private static final String TAG = "MainActivity";
    Location locationService;
    CityLocationViewModel cityLocationViewModel;
    HistoryViewModel historyViewModel;
    private String[] data = {"定位"/*,"北京","上海","广州","深圳","杭州","成都","武汉","重庆","天津","苏州","西安","安康","渭南","南京","榆林","南京","长沙","郑州","青岛"
            ,"东莞","昆明","宁波","合肥"*/};
    DrawerLayout mDrawerlayout;
    Toolbar toolbar_City;
    CityAdapter cityAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCitySearchMOhu(CityLocationViewModel cityLocationViewModel, HistoryViewModel historyViewModel) {
        this.cityLocationViewModel = cityLocationViewModel;
        this.historyViewModel = historyViewModel;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCitySearchMOhu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCitySearchMOhu newInstance(String param1, String param2,
                                                     CityLocationViewModel cityLocationViewModel, HistoryViewModel historyViewModel) {
        FragmentCitySearchMOhu fragment = new FragmentCitySearchMOhu(cityLocationViewModel,historyViewModel);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_search_m_ohu, container, false);
        /*mDrawerlayout = (DrawerLayout) view.findViewById(R.id.drawerlayout);
        //用户选择的城市数据列表
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,data);
        ListView listView = view.findViewById(R.id.recyclerchoice1);
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
                            Toast.makeText(getContext(), "这个城市添加过了", Toast.LENGTH_SHORT).show();
                        }
                        ((Activity) view.getContext()).runOnUiThread(() -> mDrawerlayout.closeDrawer(GravityCompat.END));
                    });
                } else {
                    cityLocationViewModel.researchhh(selectedCity).observe((LifecycleOwner) view.getContext(), cityLocation -> {
                        if (cityLocation == null) {
                            Location location = new Location();
                            location.Locationoo(selectedCity, new Location.OnLocationRetrievedListener() {
                                @Override
                                public void onLocationRetrieved(String locationid) {
                                    Log.d(TAG, "我得到了" + locationid);
                                    new Thread(() -> {
                                        cityLocationViewModel.insertcityLocation(new CityLocation(locationid, selectedCity, locationid));
                                        Log.d(TAG, selectedCity + locationid);
                                    }).start();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "这个城市添加过了", Toast.LENGTH_SHORT).show();
                        }
                        ((Activity) view.getContext()).runOnUiThread(() -> mDrawerlayout.closeDrawer(GravityCompat.END));
                    });
                }
            }
        });*/
        FloatingActionButton floatingActionButton = view.findViewById(R.id.folatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mDrawerlayout.openDrawer(GravityCompat.END);*/
                cityLocationViewModel.researchhh("定位").observe((LifecycleOwner) view.getContext(), cityLocation -> {
                    if (cityLocation == null) {
                        setupLocationService();
                    } else {
                        Toast.makeText(getContext(), "已经定位过了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerCityUse);
        List<CityLocation> cityLocations = new ArrayList<>();
        cityAdapter= new CityAdapter(cityLocationViewModel,cityLocations,historyViewModel);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityLocationViewModel.research().observe(getViewLifecycleOwner(), new Observer<List<CityLocation>>() {
            @Override
            public void onChanged(List<CityLocation> cityLocations) {
                cityAdapter.setCityList(cityLocations);
                cityAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
    private void setupLocationService() {
        Log.d(TAG, "我开始定位了");
        try {
            Log.d(TAG, "我yao开始定位了");
            locationService = new Location(getContext());
            locationService.startLocation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        locationService.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.d(TAG, "我yaohh开始定位了");
                String result = "";
                if (aMapLocation == null) {
                    Log.d(TAG, "定位失败，aMapLocation 为空");
                    return;
                }
                if (aMapLocation.getErrorCode() == 0) {
                    Log.d(TAG, "定位成功");
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    result += aMapLocation.getLongitude();//获取经度
                    result += ",";
                    result += aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getAccuracy();//获取精度信息
                    aMapLocation.getAddress();//详细地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    aMapLocation.getCountry();//国家信息
                    aMapLocation.getProvince();//省信息
                    aMapLocation.getCity();//城市信息
                    String location = aMapLocation.getDistrict();//城区信息
                    aMapLocation.getStreet();//街道信息
                    aMapLocation.getStreetNum();//街道门牌号信息
                    aMapLocation.getCityCode();//城市编码
                    aMapLocation.getAdCode();//地区编码
                    aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    aMapLocation.getFloor();//获取当前室内定位的楼层
                    aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                    Log.d(TAG, result);
                    Log.d(TAG, location);
                    Location location1 = new Location();
                    location1.Locationoo(result, new Location.OnLocationRetrievedListener() {
                        @Override
                        public void onLocationRetrieved(String locationId) {
                            CountDownLatch latch = new CountDownLatch(1);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HistoryMessage historyMessage = historyViewModel.researcHistoryById(locationId);
                                    Log.e("MainActivity", "观察到数据更新: " + historyMessage);
                                    if (historyMessage == null) {
                                        cityLocationViewModel.insertcityLocation(new CityLocation("定位", location, locationId));
                                        historyViewModel.insertHistory(new HistoryMessage(Integer.parseInt(locationId), location));
                                    } else {
                                        /*Toast.makeText(getContext(), "定位获取过了",Toast.LENGTH_SHORT).show();*/
                                    }
                                    latch.countDown();
                                }
                            }).start();
                            try {
                                latch.await();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "定位失败，错误：" + aMapLocation.getErrorInfo());
                }
                locationService.stopLocation();
            }
        });
    }
}