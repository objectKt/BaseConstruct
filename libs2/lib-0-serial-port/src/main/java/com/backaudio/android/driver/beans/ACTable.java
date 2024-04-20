package com.backaudio.android.driver.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 空调
 */
@Entity(tableName = "ac_table")
public class ACTable {

    @PrimaryKey
    @NonNull
    private String deviceId = "";
    // 空调是否打开
    private boolean airOpen = false;
    // A/C是否打开
    private boolean acOpen = false;
    // 内循环是否打开
    private boolean innerLoop = false;
    // 左边风速自动
    private boolean leftWindAuto = false;
    // 右边风速自动
    private boolean rightWindAuto = false;
    // DUAL是否打开
    private boolean dual = false;
    // 前座空调是否打开
    private boolean front = false;
    // 后座空调是否打开
    private boolean rear = false;
    // 风向auto是否自动打开
    private boolean windDirAuto = false;
    // 前窗除雾
    private boolean frontDemist = false;
    // 最大前窗除雾
    private boolean maxFrontDemist = false;
    // 后窗除雾
    private boolean rearDemist = false;
    // 右侧风向
    private int rightWindDire = 0;
    // 左侧风向
    private int LeftWindDire = 0;
    // 左侧风量0-7级
    private float leftWindLevel = 0f;
    // 右侧风量0-7级
    private float rightWindLevel = 0f;
    // 左侧温度，15-LO, 29-HI, 16-28显示数字
    private float leftTemp = 15;
    // 右侧温度，15-LO, 29-HI, 16-28显示数字(如果为-1则表示只有一个分区空调)
    private float rightTemp = 15;

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isAirOpen() {
        return airOpen;
    }

    public void setAirOpen(boolean airOpen) {
        this.airOpen = airOpen;
    }

    public boolean isAcOpen() {
        return acOpen;
    }

    public void setAcOpen(boolean acOpen) {
        this.acOpen = acOpen;
    }

    public boolean isInnerLoop() {
        return innerLoop;
    }

    public void setInnerLoop(boolean innerLoop) {
        this.innerLoop = innerLoop;
    }

    public boolean isLeftWindAuto() {
        return leftWindAuto;
    }

    public void setLeftWindAuto(boolean leftWindAuto) {
        this.leftWindAuto = leftWindAuto;
    }

    public boolean isRightWindAuto() {
        return rightWindAuto;
    }

    public void setRightWindAuto(boolean rightWindAuto) {
        this.rightWindAuto = rightWindAuto;
    }

    public boolean isDual() {
        return dual;
    }

    public void setDual(boolean dual) {
        this.dual = dual;
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public boolean isRear() {
        return rear;
    }

    public void setRear(boolean rear) {
        this.rear = rear;
    }

    public boolean isWindDirAuto() {
        return windDirAuto;
    }

    public void setWindDirAuto(boolean windDirAuto) {
        this.windDirAuto = windDirAuto;
    }

    public boolean isFrontDemist() {
        return frontDemist;
    }

    public void setFrontDemist(boolean frontDemist) {
        this.frontDemist = frontDemist;
    }

    public boolean isMaxFrontDemist() {
        return maxFrontDemist;
    }

    public void setMaxFrontDemist(boolean maxFrontDemist) {
        this.maxFrontDemist = maxFrontDemist;
    }

    public boolean isRearDemist() {
        return rearDemist;
    }

    public void setRearDemist(boolean rearDemist) {
        this.rearDemist = rearDemist;
    }

    public int getRightWindDire() {
        return rightWindDire;
    }

    public void setRightWindDire(int rightWindDire) {
        this.rightWindDire = rightWindDire;
    }

    public int getLeftWindDire() {
        return LeftWindDire;
    }

    public void setLeftWindDire(int leftWindDire) {
        this.LeftWindDire = leftWindDire;
    }

    public float getLeftWindLevel() {
        return leftWindLevel;
    }

    public void setLeftWindLevel(float leftWindLevel) {
        this.leftWindLevel = leftWindLevel;
    }

    public float getRightWindLevel() {
        return rightWindLevel;
    }

    public void setRightWindLevel(float rightWindLevel) {
        this.rightWindLevel = rightWindLevel;
    }

    public float getLeftTemp() {
        return leftTemp;
    }

    public void setLeftTemp(float leftTemp) {
        this.leftTemp = leftTemp;
    }

    public float getRightTemp() {
        return rightTemp;
    }

    public void setRightTemp(float rightTemp) {
        this.rightTemp = rightTemp;
    }

    @NonNull
    @Override
    public String toString() {
        return "leftTemp=" + leftTemp +
                "，rightTemp=" + rightTemp +
                "，airOpen=" + airOpen +
                "，acOpen=" + acOpen +
                "，innerLoop=" + innerLoop +
                "，leftWindAuto=" + leftWindAuto +
                "，rightWindAuto=" + rightWindAuto +
                "，dual=" + dual +
                "，front=" + front +
                "，rear=" + rear +
                "，windDirAuto=" + windDirAuto +
                "，frontDemist=" + frontDemist +
                "，maxFrontDemist=" + maxFrontDemist +
                "，rearDemist=" + rearDemist +
                "，flatWind=" + rightWindDire +
                "，frontWind=" + LeftWindDire +
                "，leftWindLevel=" + leftWindLevel +
                "，rightWindLevel=" + rightWindLevel;
    }
}
