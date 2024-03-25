package com.android.launcher.entity;

/**
* @description: 机油液位
* @createDate: 2023/9/19
*/
public class EngineOilMenuEntity {

    private String data;

    private String status1;
    private String status2;
    private String status3;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getStatus3() {
        return status3;
    }

    public void setStatus3(String status3) {
        this.status3 = status3;
    }

    @Override
    public String toString() {
        return "EngineOilMenuEntity{" +
                "data='" + data + '\'' +
                ", status1='" + status1 + '\'' +
                ", status2='" + status2 + '\'' +
                ", status3='" + status3 + '\'' +
                '}';
    }
}
