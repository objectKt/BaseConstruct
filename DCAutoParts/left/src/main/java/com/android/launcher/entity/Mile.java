package com.android.launcher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class Mile implements Serializable {

    public static final long serialVersionUID = 1L ;

    @Id(autoincrement = true)
    public Long id ;

    //总里程
    public String totleMile ;

    //用户设置里程
    public String UserMile ;

    //创建时间
    public String createDate ;

    //用户变更里程时间
    public String userChangeDate ;

    // 当前状态
    public String currentStatus ;

    @Generated(hash = 1676161150)
    public Mile() {
    }


    @Generated(hash = 608939031)
    public Mile(Long id, String totleMile, String UserMile, String createDate, String userChangeDate, String currentStatus) {
        this.id = id;
        this.totleMile = totleMile;
        this.UserMile = UserMile;
        this.createDate = createDate;
        this.userChangeDate = userChangeDate;
        this.currentStatus = currentStatus;
    }

    public Long getId() {
        return id;
    }

    public String getTotleMile() {
        return totleMile;
    }

    public void setTotleMile(String totleMile) {
        this.totleMile = totleMile;
    }

    public String getUserMile() {
        return UserMile;
    }

    public void setUserMile(String userMile) {
        UserMile = userMile;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserChangeDate() {
        return userChangeDate;
    }

    public void setUserChangeDate(String userChangeDate) {
        this.userChangeDate = userChangeDate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mile{" +
                "id=" + id +
                ", totleMile='" + totleMile + '\'' +
                ", UserMile='" + UserMile + '\'' +
                ", createDate='" + createDate + '\'' +
                ", userChangeDate='" + userChangeDate + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                '}';
    }
}
