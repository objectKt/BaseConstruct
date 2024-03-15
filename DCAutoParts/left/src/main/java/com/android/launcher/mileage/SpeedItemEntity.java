package com.android.launcher.mileage;

/**
* @description:
* @createDate: 2023/9/15
*/
public class SpeedItemEntity {

    private int speed;
    private int unitType;

    public SpeedItemEntity(int speed, int unitType) {
        this.speed = speed;
        this.unitType = unitType;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }
}
