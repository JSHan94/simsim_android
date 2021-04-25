package com.termproject.simsim.dataclass;

public class LogData {


    private String uid,time,dayOfweek, tag;
    private Double latitude, longitude;

    public LogData(){}

    public LogData(String uid, String time, String dayOfweek, String tag, Double latitude, Double longitude) {
        this.uid = uid;
        this.time = time;
        this.dayOfweek = dayOfweek;
        this.tag = tag;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDayOfweek() {
        return dayOfweek;
    }

    public void setDayOfweek(String dayOfweek) {
        this.dayOfweek = dayOfweek;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
