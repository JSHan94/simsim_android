package com.termproject.simsim.dataclass;

public class VacationData {

    private String place,date, members;

    public VacationData(){

    }

    public VacationData(String place, String date, String members) {
        this.place = place;
        this.date = date;
        this.members = members;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
