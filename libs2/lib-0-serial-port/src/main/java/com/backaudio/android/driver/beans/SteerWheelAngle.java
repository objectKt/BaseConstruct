package com.backaudio.android.driver.beans;

/**
 * 方向盘转角信息
 * @date： 2024/3/8
 * @author: 78495
*/
public class SteerWheelAngle {

    private Orientation orientation;

    private float angle;


    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public enum Orientation{
        LEFT, //左转
        RIGHT //右转
    }


    @Override
    public String toString() {
        return "SteerWheelAngle{" +
                "orientation=" + orientation +
                ", angle=" + angle +
                '}';
    }
}
