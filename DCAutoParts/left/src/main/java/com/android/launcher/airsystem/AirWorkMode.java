package com.android.launcher.airsystem;

/**
 * 空调工作模式
 */
public enum AirWorkMode {
    AUTO_MODE("1"),
    OPERATION_MODE("0");

    private String value;

    private AirWorkMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
