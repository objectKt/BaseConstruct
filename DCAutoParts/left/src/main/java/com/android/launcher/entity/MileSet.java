package com.android.launcher.entity;

public class MileSet {

    public Long id ;

    public String setTime ;

    public String setMile ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public String getSetMile() {
        return setMile;
    }

    public void setSetMile(String setMile) {
        this.setMile = setMile;
    }

    public MileSet() {
    }

    public MileSet(Long id, String setTime, String setMile) {
        this.id = id;
        this.setTime = setTime;
        this.setMile = setMile;
    }

    @Override
    public String toString() {
        return "MileSet{" +
                "id=" + id +
                ", setTime='" + setTime + '\'' +
                ", setMile='" + setMile + '\'' +
                '}';
    }
}
