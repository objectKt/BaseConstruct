package com.android.launcher.can;

import android.annotation.SuppressLint;

import com.android.launcher.usbdriver.SendHelperUsbToRight;

import java.util.List;

//后空调
public class Canbc implements  CanParent{

    private String lastData = "";

    @SuppressLint("NewApi")
    @Override
    public void handlerCan(List<String> msg) {

        if (msg.size()>0){
            msg.set(1,"06") ;
            String senddata = String.join("" ,msg) ;

            if (lastData.equals(senddata)) {
                return;
            }
            lastData = senddata;
            String send ="AABB"+senddata+"CCDD" ;
            SendHelperUsbToRight.handler(send.toUpperCase());
        }
    }
}