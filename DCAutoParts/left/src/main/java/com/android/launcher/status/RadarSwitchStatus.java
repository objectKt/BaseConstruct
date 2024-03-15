package com.android.launcher.status;

/**
* @description: 雷达开关状态
* @createDate: 2023/5/24
*/
public enum RadarSwitchStatus {

    STATE_ON("80"), //开
    STATE_OFF("00"); //关

    private String status;

    RadarSwitchStatus(String status){
        this.status = status;
    }

    public String getValue(){
        return status;
    }
}
