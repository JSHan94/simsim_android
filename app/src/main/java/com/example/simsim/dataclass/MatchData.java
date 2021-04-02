package com.example.simsim.dataclass;

import com.example.simsim.adapter.MatchAdapter;

public class MatchData {
    private String userName;
    private String tag;
    private double latitude;
    private double longitude;

    public MatchData(){

    }
    public MatchData(String userName, String tag, double latitude, double longitude){
        this.userName = userName;
        this.tag = tag;
        this.latitude =latitude;
        this.longitude = longitude;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



}
