package com.example.sunnyweather.history;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather.SQLiteHelp.CityLocation;
import com.example.sunnyweather.SQLiteHelp.CityLocationDao;
import com.example.sunnyweather.SQLiteHelp.CityLocationRepository;

import java.util.List;

public class HistoryRepository {
    private HistoryDao historyDao;

    public HistoryRepository(Context context) {
        HistoryDataBase historyDataBase = HistoryDataBase.getInstance(context);
        this.historyDao = historyDataBase.getHistoryDao();
    }
    //对数据进行添加
    public void insertHistory(HistoryMessage... historyMessages) {
        new HistoryRepository.InsertHistoryTask(historyDao).execute(historyMessages);
    }
    class InsertHistoryTask extends AsyncTask<HistoryMessage, Void, Void> {
        private HistoryDao historyDao;
        public InsertHistoryTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }
        @Override
        protected Void doInBackground(HistoryMessage... historyMessages) {
            historyDao.insertHistory(historyMessages);
            return null;
        }
    }

    //对数据进行删除
    public void deleteHistory(HistoryMessage... historyMessages) {
        new HistoryRepository.DeleteHistoryTask(historyDao).execute(historyMessages);
    }
    class DeleteHistoryTask extends AsyncTask<HistoryMessage, Void, Void> {
        private HistoryDao historyDao;
        public DeleteHistoryTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }
        @Override
        protected Void doInBackground(HistoryMessage... historyMessages) {
            historyDao.deleteHistory(historyMessages);
            return null;
        }
    }
    //对所有数据进行查找
    public LiveData<List<HistoryMessage>> getAllHistoryMessageLive() {
        return historyDao.getAllHistoryMessageLive();
    }
    //根据ID进行查找
    public HistoryMessage getHistoryMessageById(String cityID) {
        return historyDao.getHistoryMessageById(cityID);
        /*return new MutableLiveData<HistoryMessage>() {
            @Override
            protected void onActive() {
                super.onActive();
                loadCityLocationByID(cityID);
            }
            private void loadCityLocationByID(String cityID) {
                new Thread(() -> {
                    HistoryMessage historyMessage = historyDao.getHistoryMessageById(cityID);
                    postValue(historyMessage);
                }).start();
            }
        };*/
    }
    //对数据进行修改
    public void updateHistory(HistoryMessage... historyMessages) {
        new HistoryRepository.UpdataHistoryTask(historyDao).execute(historyMessages);
    }
    class UpdataHistoryTask extends AsyncTask<HistoryMessage, Void, Void> {
        private HistoryDao historyDao;
        public UpdataHistoryTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(HistoryMessage... historyMessages) {
            historyDao.updateHistory(historyMessages);
            return null;
        }
    }
}
