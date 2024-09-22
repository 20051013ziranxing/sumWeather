package com.example.sunnyweather.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sunnyweather.SQLiteHelp.CityLocation;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository historyRepository;
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        this.historyRepository = new HistoryRepository(application);
    }
    public void insertHistory(HistoryMessage... historyMessages) {
        historyRepository.insertHistory(historyMessages);
    }
    public void deleteHistory(HistoryMessage... historyMessages) {
        historyRepository.deleteHistory(historyMessages);
    }
    public LiveData<List<HistoryMessage>> researchHistoryAll() {
        return historyRepository.getAllHistoryMessageLive();
    }
    public HistoryMessage researcHistoryById(String cityID) {
        return historyRepository.getHistoryMessageById(cityID);
    }
    public void updateHistory(HistoryMessage... historyMessages) {
        historyRepository.updateHistory(historyMessages);
    }
}
