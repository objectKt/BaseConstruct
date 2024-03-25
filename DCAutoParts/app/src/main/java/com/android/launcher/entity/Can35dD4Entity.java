package com.android.launcher.entity;

import com.android.launcher.util.LogUtils;

/**
* @description:
 *                  D1 D2 D3 D4 D5 D6
*                  00 00 75 50 F0 E5
* @createDate: 2023/6/6
*/
public class Can35dD4Entity {

    //上下车辅助功能，转向柱和座位
    //0和2两位共同作用:              bit2        bit0
    //上下车辅助功能：关闭			0		0
    //上下车辅助功能：转向柱			0		1
    //转向柱和座位			        1		1
    private char bit0 = '1';
    private static final char bit1 = '0';
    private char bit2 = '1';
    private static final char bit3 = '0';
    //自动落锁，0=关闭， 1开启
    private char bit4 = '1';
    private static final char bit5 = '0';
    //背景照明 0关闭，1开启
    private char bit6 = '1';
    private static final char bit7 = '0';

    public char getAutoLock() {
        return bit4;
    }

    public void setAutoLock(SwitchStatus status) {
        this.bit4 = status.getValue().charAt(0);
    }

    /**
    * @description: 获取上下车辅助
    * @createDate: 2023/6/7
    */
    public String getAssist(){
        return Character.toString(bit0) + bit2;
    }

    public char getLighting() {
        return bit6;
    }

    public void setLighting(SwitchStatus status) {
        this.bit6 = status.getValue().charAt(0);
    }

    /**
    * @description: 设置上下车辅助状态
    * @createDate: 2023/6/6
    */
    public void setAssist(AssistStatus assistStatus){
         if(assistStatus == AssistStatus.STEERING_COLUMN){
             bit0 = '1';
             bit2 = '0';
        }else if(assistStatus == AssistStatus.STEERING_COLUMN_AND_SEAT){
             bit0 = '1';
             bit2 = '1';
        }else{
             bit0 = '0';
             bit2 = '0';
        }
    }

    public synchronized String getBinaryData(){
        String data = String.valueOf(bit7) +
                bit6 +
                bit5 +
                bit4 +
                bit3 +
                bit2 +
                bit1 +
                bit0;
        LogUtils.printI(Can35dD4Entity.class.getSimpleName(), "getData---="+data);
        return data;
    }

    public String getHexData() {
        try {
            String binaryData = getBinaryData();
            int parseInt = Integer.parseInt(binaryData, 2);
            String hexData = Integer.toHexString(parseInt);
            LogUtils.printI(Can35dD4Entity.class.getSimpleName(), "hexData---="+hexData);
            return hexData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "55"; //全开
    }

    /**
    * @description: 上下车辅助状态
    * @createDate: 2023/6/6
    */
    public enum AssistStatus{
        STATUS_CLOSE("00"), //上下车辅助关闭
        STEERING_COLUMN("01"), //转向柱
        STEERING_COLUMN_AND_SEAT("11"); //转向柱和座位

        private String value;

        AssistStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SwitchStatus{

        STATUS_ON("1"),
        STATUS_OFF("0");

        private String value;

        SwitchStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
