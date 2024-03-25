package com.android.launcher.util;

public class ShellCommond {


    public static final String QUERY = "echo -e \"AT+TS\\r\\n\" > /dev/ttymxc4";
    public static final String QUERY_BLUETOOTH_NAME ="echo -e \"AT+TD\\r\\n\" > /dev/ttymxc4" ;
    public static final String QUERY_BLUETOOTH_SOUND ="echo -e \"AT+QN\\r\\n\" > /dev/ttymxc4" ;


    public static final String COMMON_BLUETOOTH_MUSIC_NEXT = "echo -e \"AT+CC\\r\\n\" > /dev/ttymxc4" ;

    public static final String COMMON_BLUETOOTH_MUSIC_UP = "echo -e \"AT+CD\\r\\n\" > /dev/ttymxc4" ;

    public static final String COMMON_ALERT_BLUETOOTH_PAUSE_PLAY = "echo -e \"AT+CB\\r\\n\" > /dev/ttymxc4" ;

    public static final String COMMOND_ALERT_BOUETOOTH_SOUND ="echo -e \"AT+CN01\\r\\n\" > /dev/ttymxc4" ;
    public static final String COMMOND_ALERT_BLUETOOTH_NAME = "echo -e \"AT+BDBENZ-BT\\r\\n\" > /dev/ttymxc4";

    // 拒接
    public static final String COMMOND_BLUETOOTH_REFUSE = "echo -e \"AT+BA02\\r\\n\" > /dev/ttymxc4" ;

    //挂断
    public static final String COMMOND_BLUETOOTH_HANGUP = "echo -e \"AT+BA03\\r\\n\" > /dev/ttymxc4" ;

    //接听
    public static final String COMMOND_BLUETOOTH_ANSWER = "echo -e \"AT+BA04\\r\\n\" > /dev/ttymxc4" ;


    //手机号拨打
    public static final String COMMOND_BLUETOOTH_PHONE = "echo -e \"AT+BT???????????\\r\\n\"" ;



}
