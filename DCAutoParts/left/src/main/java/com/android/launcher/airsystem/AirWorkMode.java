package com.android.launcher.airsystem;

/**
* @description: 空调工作模式
* @createDate: 2023/5/2
*/
public enum  AirWorkMode{
    AUTO_MODE("1"),
    OPERATION_MODE("0");

   private String value;

    private AirWorkMode(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
