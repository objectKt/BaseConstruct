package com.android.launcher.can;


import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.LogUtils;

import java.util.List;

//风向调整
public class Canbb implements CanParent {

    //空调风向数据
    public static String AIR_SYS_STATE_VALUE_BB;

    private String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {
        if(msg != null){
            //[bb, 5, 33, 00, 6C, 07, FF, 00, 00, 00]
            if (msg.size()>0){
                msg.set(1,"05") ;
                String senddata = String.join("" ,msg) ;

                if (lastData.equals(senddata)) {
                    return;
                }
                lastData = senddata;

                LogUtils.printI(Canbb.class.getName(),senddata);
                setStateValue(senddata);
                String send ="AABB"+senddata+"CCDD" ;
                SendHelperUsbToRight.handler(send.toUpperCase());
            }
        }

    }

    public static synchronized void setStateValue(String senddata) {
        if (!senddata.equals(AIR_SYS_STATE_VALUE_BB)) {
            AIR_SYS_STATE_VALUE_BB = senddata;
        }
    }
}
