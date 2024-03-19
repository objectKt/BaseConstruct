package com.android.launcher.usbdriver;

import com.android.launcher.ttl.SerialHelperTTLd;
import com.android.launcher.util.LogUtils;

import dc.library.auto.task.logger.TaskLogger;

public class SendHelperUsbToRight {

    public static volatile boolean bandFlg = false;

    public static void handler(String hex) {
        try {
            LogUtils.printI(SendHelperUsbToRight.class.getName(), "hex=" + hex);
            String start = hex.substring(0, 4);
            String data = hex.substring(4, hex.length() - 4);
            String end = hex.substring(hex.length() - 4);
            TaskLogger.i("hex="+hex +", data="+data);
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
            TaskLogger.i("newData=" + newData);
            SerialHelperTTLd.sendHex(newData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
