package com.android.launcher.vo;


/**
 * 手机号码
 */
public class PhoneVo {

    public String id;
    public String phoneUserName ;
    public String phoneNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneUserName() {
        return phoneUserName;
    }

    public void setPhoneUserName(String phoneUserName) {
        this.phoneUserName = phoneUserName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", phoneUserName='" + phoneUserName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
