package com.android.launcher.entity;

import java.io.Serializable;

public class Mile implements Serializable {

    public static final long serialVersionUID = 1L ;

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

    public Mile() {
    }

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
