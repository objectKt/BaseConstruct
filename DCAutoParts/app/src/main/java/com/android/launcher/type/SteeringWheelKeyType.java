package com.android.launcher.type;

/**
 * 已搬运
* @description: 方向盘按键类型
* @createDate: 2023/9/20
*/
public enum SteeringWheelKeyType {
    UP("5"),
    DOWN("6"),
    LEFT("7"),
    RIGHT("8"),
    OK("9"),
    BACK("10");

    private String typeValue;

    SteeringWheelKeyType(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
