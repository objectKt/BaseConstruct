package com.android.launcher.vo;

public class AlertVo {

    //车门提示，还是普通提示
    private Type type = Type.NORMAL;

    String alertMessage;


    String alertColor;

    int alertImg;

    private boolean isShow = true;

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertColor() {
        return alertColor;
    }

    public void setAlertColor(String alertColor) {
        this.alertColor = alertColor;
    }

    public int getAlertImg() {
        return alertImg;
    }

    public void setAlertImg(int alertImg) {
        this.alertImg = alertImg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "AlertVo{" +
                "type=" + type +
                ", alertMessage='" + alertMessage + '\'' +
                ", alertColor='" + alertColor + '\'' +
                ", alertImg=" + alertImg +
                ", isShow=" + isShow +
                '}';
    }

    @Override
    public int hashCode() {
        return alertMessage.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        AlertVo alertVo = (AlertVo) obj;
        return this.alertMessage.equals(alertVo.alertMessage);
    }

    public enum Type{
        //车门
        DOOR,
        //底盘升降
        CHASSIS,
        //正常
        NORMAL
    }

}
