package com.example.simsim.dataclass;

public class MatchData {
    private String userName;
    private String tag;
    private int distance;

    public MatchData(String userName, String tag, int distance){
        this.userName = userName;
        this.tag = tag;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
