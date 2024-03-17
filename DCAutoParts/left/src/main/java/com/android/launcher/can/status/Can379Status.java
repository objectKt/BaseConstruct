package com.android.launcher.can.status;

public enum Can379Status {

    STA_OFF("00"),
    STA_LIFT("1"), //车身升起
    STA_TO_LOW("1"), //"停车\n车身太低
    STA_LIFT_NOW("2"), //车辆正在被升起请稍后
    STA_BREAKDOWN("4"); //故障
    private String value;

    public String getValue() {
        return value;
    }

    Can379Status(String value) {
        this.value = value;
    }
}