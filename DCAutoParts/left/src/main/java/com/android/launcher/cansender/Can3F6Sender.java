package com.android.launcher.cansender;

import com.android.launcher.cruisecontrol.CanSenderBase;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;

/**
 * @description:
 * @createDate: 2023/8/17
 */
public class Can3F6Sender extends CanSenderBase {


    private String dataLength = "05";

    private static final String ID = "000003F6";

    private String d1 = "00";
    private String d2 = "00";
    private String d3 = "00";
    private String d4 = "00";
    private String d5 = "00";
    private String d6 = "00";
    private String d7 = "00";
    private String d8 = "00";


    @Override
    public void send() {

    }


    public void toMil(){
        try {

            d1 = "02";
            String sendData = DATA_HEAD + dataLength + ID + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8;
            LogUtils.printI(Can3F6Sender.class.getSimpleName(), "toMil---sendData="+sendData + ", length="+sendData.length());
            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
