package com.example.sunnyweather;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.sunnyweather.Adapter.searchcityAdapter;
import com.example.sunnyweather.SQLiteHelp.CityLocationViewModel;
import com.example.sunnyweather.ViewModel.searchCityViewModel;
import com.example.sunnyweather.bean.searchCity;
import com.example.sunnyweather.databinding.FragmentSearchCityBinding;
import com.example.sunnyweather.history.HistoryViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchCityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchCityFragment extends Fragment {
    private searchCityViewModel viewModel;
    private Observer<searchCity> cityDataObserver;
    private EditText editText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CityLocationViewModel cityLocationViewModel;
    HistoryViewModel historyViewModel;

    public searchCityFragment(CityLocationViewModel cityLocationViewModel, HistoryViewModel historyViewModel) {
        // Required empty public constructor
        this.cityLocationViewModel = cityLocationViewModel;
        this.historyViewModel = historyViewModel;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchCityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchCityFragment newInstance(String param1, String param2, CityLocationViewModel cityLocationViewModel,
                                                 HistoryViewModel historyViewModel) {
        searchCityFragment fragment = new searchCityFragment(cityLocationViewModel, historyViewModel);
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);
        editText = view.findViewById(R.id.editViewSearchCity);
        viewModel = new ViewModelProvider(this).get(searchCityViewModel.class);
        cityDataObserver = searchCity -> {
            if (searchCity != null && searchCity.getLocation() != null) {
                FragmentSearchCityBinding fragmentSearchCityBinding = DataBindingUtil.bind(view);
                List<searchCity.Location> locations = searchCity.getLocation();
                RecyclerView recyclerView = fragmentSearchCityBinding.recyclerViewSearchCity;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                searchcityAdapter searchcityAdapter = new searchcityAdapter(locations, cityLocationViewModel,historyViewModel, (CityManage) getContext());
                recyclerView.setAdapter(searchcityAdapter);
            }
        };
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), cityDataObserver);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}