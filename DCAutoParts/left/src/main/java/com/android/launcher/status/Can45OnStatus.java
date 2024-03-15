package com.android.launcher.status;

/**
* @description: Can45 某个按键开启状态
* @createDate: 2023/5/6
*/
public enum Can45OnStatus {

    STATE_ON("1"), //开
    STATE_OFF("0"); //关

    private String status;

    Can45OnStatus(String status){
        this.status = status;
    }

    public String getValue(){
        return status;
    }
}
