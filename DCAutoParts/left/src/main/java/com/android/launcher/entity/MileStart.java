package com.android.launcher.entity;

public class MileStart {

    public Long id ;

    public String startTime ;

    public String startMile ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartMile() {
        return startMile;
    }

    public void setStartMile(String startMile) {
        this.startMile = startMile;
    }

    public MileStart() {
    }

    public MileStart(Long id, String startTime, String startMile) {
        this.id = id;
        this.startTime = startTime;
        this.startMile = startMile;
    }

    @Override
    public String toString() {
        return "MileStart{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", startMile='" + startMile + '\'' +
                '}';
    }
}
