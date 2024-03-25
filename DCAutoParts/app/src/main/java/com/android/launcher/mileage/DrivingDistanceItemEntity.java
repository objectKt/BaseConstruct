package com.android.launcher.mileage;

import com.android.launcher.type.UnitType;

/**
* @description:
* @createDate: 2023/9/15
*/
public class DrivingDistanceItemEntity {

    //可行驶距离
    private float distance;

    //油耗
    private float qtrip;

    private int unitType = UnitType.getDefaultType();

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getQtrip() {
        return qtrip;
    }

    public void setQtrip(float qtrip) {
        this.qtrip = qtrip;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }
}
