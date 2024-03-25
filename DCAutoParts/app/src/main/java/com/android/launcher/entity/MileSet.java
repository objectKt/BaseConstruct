package com.android.launcher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class MileSet {

    @Id(autoincrement = true)
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

    @Generated(hash = 1803843700)
    public MileSet() {
    }

    @Generated(hash = 865034844)
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
