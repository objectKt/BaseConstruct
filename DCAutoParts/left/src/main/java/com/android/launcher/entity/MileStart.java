package com.android.launcher.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class MileStart {

    @Id(autoincrement = true)
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

    @Generated(hash = 1155948733)
    public MileStart() {
    }

    @Generated(hash = 857341015)
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
