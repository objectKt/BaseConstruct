package com.android.launcher.usbdriver;

import com.android.AppConfiguration;
import com.android.launcher.ttl.SerialHelperttlLd;
import com.android.launcher.util.LogUtils;

/**
 * @date： 2024/3/14
 * @author: 78495
*/
public class SendHelperUsbToRight {

    public static volatile boolean bandFlg = false;

    public static void handler(String hex) {
        try {
//            Thread.sleep(20);
            LogUtils.printI(SendHelperUsbToRight.class.getName(), "hex=" + hex);

//            WifiServerSocket.getInstance().sendeMessage(hex);

            if(AppConfiguration.TTL_ADD_CHECK_CODE){
                String start = hex.substring(0, 4);


                String data = hex.substring(4, hex.length() - 4);
                String end = hex.substring(hex.length() - 4);

                LogUtils.printI(SendHelperUsbToRight.class.getSimpleName(),"hex="+hex +", data="+data);

                char[] chars = data.toCharArray();

                int length = data.length();

                int checkNum = length;
                for (int i = 0; i < chars.length; i++) {
                    byte bt = (byte) chars[0];
                    checkNum += bt;
                }
                byte bt = (byte) checkNum;

                checkNum = bt;

                String checkNumHex = Integer.toHexString(checkNum);
                if (checkNumHex.length() == 1) {
                    checkNumHex = "0" + checkNumHex;
                }

                String lengthHex = Integer.toHexString(length);
                if (lengthHex.length() == 1) {
                    lengthHex = "0" + lengthHex;
                }

                LogUtils.printI(SendHelperUsbToRight.class.getSimpleName(), "checkNum=" + checkNum + ", checkNumHex=" + checkNumHex +", lengthHex="+lengthHex);

                String newData = start + data + lengthHex + checkNumHex + end;
//            SerialHelperttlLd.sendHex(newData);

                LogUtils.printI(SendHelperUsbToRight.class.getName(), "newData=" + newData);

                SerialHelperttlLd.sendHex(newData);
            }else{
                SerialHelperttlLd.sendHex(hex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
