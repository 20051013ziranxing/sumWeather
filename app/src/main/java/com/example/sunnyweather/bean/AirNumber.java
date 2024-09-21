package com.example.sunnyweather.bean;

import android.view.View;
import android.widget.Toast;

import java.util.List;


public class AirNumber {
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public Refer getRefer() {
        return refer;
    }

    public void setRefer(Refer refer) {
        this.refer = refer;
    }

    private String updateTime;
    private String fxLink;
    private List<Daily> daily;
    private Refer refer;
    public String getCode() {
        return code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }
    public void onClick(View view) {
        // 根据 Daily 对象的数据来显示不同的提示
        String message = daily.get(0).category != null ? "Name: " + daily.get(0).category : "No data";
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    public class Daily {
        private String date;
        private String type;
        private String name;
        private String level;
        public String category;
        private String text;
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public class Refer {
        private List<String> sources;
        private List<String> license;
    }
}
