package com.termproject.simsim.dataclass;

public class MatchData {
    private String userName,time,status;
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

    public MatchData(String userName, String tag, double latitude, double longitude, String time){
        this.userName = userName;
        this.tag = tag;
        this.latitude =latitude;
        this.longitude = longitude;
        this.time = time;
    }
    public MatchData(String userName, String tag, double latitude, double longitude, String time,String status){
        this.userName = userName;
        this.tag = tag;
        this.latitude =latitude;
        this.longitude = longitude;
        this.time = time;
        this.status =status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
