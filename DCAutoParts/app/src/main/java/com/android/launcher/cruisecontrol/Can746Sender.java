package com.android.launcher.cruisecontrol;

import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;

/**
 * @description:
 * @createDate: 2023/5/30
 */
public class Can746Sender extends CanSenderBase {

    private String data = "0048100000280000";

    private String dataLength = "08";
    private static final String ID = "00000746";

    @Override
    public void send() {
        isRunnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunnable) {
                    if(!isPause){
                        String sendData = DATA_HEAD + dataLength + ID + data;
//                    LogUtils.printI(Can746Sender.class.getSimpleName(), "sendData="+sendData + ", length="+sendData.length());
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();
    }
}
