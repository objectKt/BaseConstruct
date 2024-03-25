package com.android.launcher.status;

/**
* @description: 驾驶模式
* @createDate: 2023/5/24
*/
public enum DriveModeStatus {

    C("C"),
    M("M"),
    S("S");

    private String status;

    DriveModeStatus(String status){
        this.status = status;
    }

    public String getValue(){
        return status;
    }
}
