package com.android.launcher.meter;

/**
 * 已搬运
* @description: Fragment类型
* @createDate: 2023/6/3
*/
public enum MeterFragmentType {
    SPORT(1), //运动
    CLASSIC(2), //经典
    Tech(3), //前卫
    MAINTAIN(4), //保养
    MENU(5), //菜单
    MAP(6); //地图仪表

    MeterFragmentType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

}
