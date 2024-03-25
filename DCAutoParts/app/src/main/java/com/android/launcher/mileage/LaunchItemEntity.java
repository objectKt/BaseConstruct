package com.android.launcher.mileage;

import com.android.launcher.type.UnitType;

/**
* @description:
* @createDate: 2023/9/15
*/
public class LaunchItemEntity {

    private float userMileage = 0.00f;

    private String userTime = "0.00";

    private float totalMileage = 0.00f;

    private String totalTime = "0.00";

    private float qtrip = 0.0f;

    private float speed = 0f;

    private int unitType = UnitType.getDefaultType();


    public float getUserMileage() {
        return userMileage;
    }

    public void setUserMileage(float userMileage) {
        this.userMileage = userMileage;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public float getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(float totalMileage) {
        this.totalMileage = totalMileage;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public float getQtrip() {
        return qtrip;
    }

    public void setQtrip(float qtrip) {
        this.qtrip = qtrip;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }
}
